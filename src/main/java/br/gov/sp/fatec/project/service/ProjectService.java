package br.gov.sp.fatec.project.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.service.EntrepreneurService;
import br.gov.sp.fatec.project.domain.*;
import br.gov.sp.fatec.project.domain.Date;
import br.gov.sp.fatec.project.repository.ProjectRepository;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.exception.StudentException;
import br.gov.sp.fatec.student.service.StudentService;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.exception.TeacherException;
import br.gov.sp.fatec.teacher.service.TeacherService;
import br.gov.sp.fatec.user.domain.User;
import br.gov.sp.fatec.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.*;

import static br.gov.sp.fatec.utils.exception.InactiveException.*;
import static br.gov.sp.fatec.utils.exception.NotFoundException.*;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private EntrepreneurService entrepreneurService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String currentPrincipalName = authentication.getName();

    @Transient
    @Value("${picklejar.jwt.secret}")
    private String secret;

    // botar no userService
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userService.findByEmail(authentication.getName());
            return user.getId();
        }
        return null;
    }
//    @PreAuthorize("hasRole('REPRESENTATIVE')")
    public Project save(Project project) {
        getUserId();

        Entrepreneur found = entrepreneurService.findById(getUserId());
        throwIfEntrepreneurIsNull(found);
        throwIfEntrepreneurIsInactive(found);
        project.setEntrepreneur(found);

        if (project.getTeacher() != null && project.getTeacher().getId() != null) {
            Teacher teacherFound = teacherService.findById(project.getTeacher().getId());
            throwIfTeacherIsNull(teacherFound);
            throwIfTeacherIsInactive(teacherFound);
            project.setTeacher(teacherFound);
        }

        if (project.getStudents().size() > 0) {
            List<Student> studentList = new LinkedList<>();
            for (Student student : project.getStudents()) {
                Student studentFound = studentService.findById(student.getId());
                throwIfStudentIsNull(studentFound);
                throwIfStudentIsInactive(studentFound);
                studentList.add(studentFound);
            }
            project.setStudents(studentList);
        }
        project.setCreatedAt(ZonedDateTime.now());
        project.setProgress(1);
        Project returnProject = repository.save(project);
        checkIfFieldIsNull(returnProject);
        return returnProject;
    }

//    @PreAuthorize("hasAnyRole('CADI', 'REPRESENTATIVE', 'STUDENT', 'TEACHER')")
    public List<Project> findAll(Long id) {
    // todo -  pegar id pelo back
//        userService.search(getUserId(token));
       String authorization = userService.search(id).getAuthorizations().get(0).getName();

       List<Project> projects = new ArrayList<>();

       if (authorization.equals("REPRESENTATIVE")) {
           projects = getProjectByEntrepreneur(id);
       } else if (authorization.equals("STUDENT")) {
           projects = getProjectByStudent(id);
       } else if (authorization.equals("CADI")) {
//           projects = getProjectByStudent(id);
           projects = repository.findAll();
       } else if (authorization.equals("TEACHER")) {
           projects = getProjectByTeacher(id);
       }

//        List<Project> projects = repository.findAll();

        for (Project project : projects) {
            checkIfFieldIsNull(project);
        }

        return projects;
    }

    private void checkIfFieldIsNull(Project project) {
        if (project.getStudents() == null) {
            project.setStudents(new ArrayList<>());
        }

        if (project.getEntrepreneur() == null) {
            project.setEntrepreneur(new Entrepreneur());
        }

        if (project.getTeacher() != null) {
            project.setTeacher(new Teacher());
        }

        if (project.getMeeting() == null) {
            project.setMeeting(new Meeting());
        }

        if (project.getStudentResponsible() == null) {
            project.setStudentResponsible(new Student());
        }

        if (project.getMeeting() == null) {
            project.setMeeting(new Meeting());
        }

        if (project.getMeeting().getAddress() == null) {
            project.getMeeting().setAddress(new Address());
        }
    }

//    @PreAuthorize("hasAnyRole('CADI', 'REPRESENTATIVE', 'STUDENT', 'TEACHER')")
    public Project findById(Long id) {
        Project project =  repository.findById(id).orElse(null);
        throwIfProjectIsNull(project, id);
        checkIfFieldIsNull(project);
        return project;
    }

//    @PreAuthorize("hasRole('REPRESENTATIVE')")
    public void delete(Long id) {
        Project project = findById(id);
        throwIfProjectIsNull(project, id);

        repository.delete(project);
    }

    public long getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

//    @PreAuthorize("hasRole('TEACHER')")
    public Project setStudentResponsible(Long projectId, Long studentId, Long teacherId) {
        checkIfCanAddStudentToProject(teacherId, projectId);
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        Student student = studentService.findById(studentId);
        throwIfStudentIsNull(student, studentId);
        throwIfStudentIsInactive(student);

        project.setStudentResponsible(student);

//        if (student.getProjects() == null) {
//            student.setProjects(new ArrayList<>());
//        }
//        addStudent(projectId, studentId);

        checkIfFieldIsNull(project);
        return repository.save(project);
    }

    private void checkIfCanAddStudentToProject(Long teacherId, Long projectId) {
        Teacher teacher = teacherService.findById(teacherId);
        throwIfTeacherIsNull(teacher, teacherId);
        throwIfTeacherIsInactive(teacher);

        Project project = findById(projectId);
        throwIfProjectIsNull(project);

        if (project.getTeacher() == null || !project.getTeacher().getId().equals(teacher.getId())) {
            throw new TeacherException.CannotAddOrRemoveStudentsToThisProject();
        }
    }

//    @PreAuthorize("hasRole('TEACHER')")
    // serve para editar também. ele sobrescreve. Só deve ser passado todos os alunos
    public Project setStudents(Long projectId, List<Student> studentList, Long teacherId) {
        checkIfCanAddStudentToProject(teacherId, projectId);
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        List<Student> students = new LinkedList<>();

        for (Student student : studentList) {
            Student found = studentService.findById(student.getId());
            throwIfStudentIsNull(found, student.getId());
            throwIfStudentIsInactive(found);

            students.add(found);
        }
        checkIfFieldIsNull(project);
        project.setStudents(students);
        return repository.save(project);
    }

//    @PreAuthorize("hasRole('TEACHER')")
    public Project addStudent(Long projectId, Long studentId) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        Student student = studentService.findById(studentId);
        throwIfStudentIsNull(student, studentId);
        throwIfStudentIsInactive(student);

        project.getStudents().add(student);
        checkIfFieldIsNull(project);
        return repository.save(project);
    }

//    @PreAuthorize("hasRole('CADI')")
    public Project setTeacher(Long projectId, Long teacherId) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        Teacher teacher = teacherService.findById(teacherId);
        throwIfTeacherIsNull(teacher, teacherId);
        throwIfTeacherIsInactive(teacher);

        project.setTeacher(teacher);
        checkIfFieldIsNull(project);
        return repository.save(project);
    }

//    @PreAuthorize("hasRole('TEACHER')")
    public Project removeStudent(Long projectId, Long StudentId) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        Student student = studentService.findById(StudentId);
        throwIfStudentIsNull(student, StudentId);

        project.getStudents().remove(student);
        checkIfFieldIsNull(project);
        return repository.save(project);
    }

    public List<Project> getProjectByTeacher(Long teacherId) {
        List<Project> projects = repository.findByTeacherId(teacherId);
        for (Project project : projects) {
            checkIfFieldIsNull(project);
        }

        return projects;
    }

    public List<Project> getProjectByStudent(Long studentId) {
        List<Project> projects = repository.findByStudentsId(studentId);
        for (Project project : projects) {
            checkIfFieldIsNull(project);
        }

        return projects;
    }

    public List<Project> getProjectByStudentResponsible(Long studentId) {
        List<Project> projects = repository.findByStudentResponsibleId(studentId);
        for (Project project : projects) {
            checkIfFieldIsNull(project);
        }
        return projects;
    }

    public List<Project> getProjectByEntrepreneur(Long entrepreneurId) {
        List<Project> projects = repository.findByEntrepreneurId(entrepreneurId);
        for (Project project : projects) {
            checkIfFieldIsNull(project);
        }
        return projects;
    }

//    @PreAuthorize("hasRole('STUDENT')")
    public Project setSolution(Long projectId, Deliver deliver) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project);

        project.setProgress(6);
        project.getDeliver().add(deliver);
        return repository.save(project);
    }

//    @PreAuthorize("hasRole('REPRESENTATIVE')")
    public Project update(Project project) {
        Project found = findById(project.getId());
        throwIfProjectIsNull(found);

        found.setProgress(getProgress(found));
        found.setCompleteDescription(project.getCompleteDescription());
        found.setTechnologyDescription(project.getTechnologyDescription());
        found.setTitle(project.getTitle());
        found.setShortDescription(project.getShortDescription());
        found.setNotes(project.getNotes());

        return repository.save(found);
    }

    private int getProgress (Project project) {
        if (project.getCompleteDescription() != null && project.getTechnologyDescription() != null && project.getProgress() == 2) {
            return 3;
        } if (project.getProgress() == 3 && project.getCompleteDescription() != null && project.getTechnologyDescription() != null) {
            return 4;
        } else if (project.getProgress() == 1 && project.getCompleteDescription() == null && project.getTechnologyDescription() == null) {
            return 2;
        } else if (project.getProgress() == 3 && project.getMeeting() != null && project.getMeeting().getPossibleDate().size() > 0) {
            return 5;
        } else if (project.getProgress() == 5 && project.getDeliver().size() > 0) {
            return 6;
        }
        else {
            return project.getProgress();
        }
    }

//    @PreAuthorize("hasRole('CADI')")
    public Project setMeetingPossibleDate(List<Date> possibleDate, Long projectId) {
        for (Date date : possibleDate) {
            throwIfDateIsNull(date);
        }

        Project project = findById(projectId);
        throwIfProjectIsNull(project);

        Meeting meeting = new Meeting();
        meeting.setPossibleDate(possibleDate);

        project.setMeeting(meeting);
        project.setProgress(5);

        return repository.save(project);
    }

//    @PreAuthorize("hasRole('REPRESENTATIVE')")
    public Project setMeetingChosenDate(Long possibleDateId, Long projectId) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project);

        List<Date> possibleDateList = project.getMeeting().getPossibleDate();

        Date date = possibleDateList.stream().filter(
                possibleDate -> possibleDateId.equals(possibleDate.getId()))
                .findFirst().orElse(null);

        throwIfDateIsNull(date);

        project.getMeeting().setChosenDate(date.getDateTime());

        return repository.save(project);
    }

//    @PreAuthorize("hasRole('CADI')")
    public Project approve(Long id) {
        Project project = findById(id);
        throwIfProjectIsNull(project);

        project.setProgress(getProgress(project));
        return repository.save(project);
    }

    public Map<String, List<Project>> findProjectByStudent(Long studentId) {
        Map<String, List<Project>> projects = new HashMap<>();
        projects.put("responsible", getProjectByStudentResponsible(studentId));
        projects.put("team", getProjectByStudent(studentId));

        return projects;
    }

//    @PreAuthorize("hasRole('STUDENT')")
    public Project setSolution(Deliver deliver, Long projectId) {
        // todo - pegar id do aluno responsavel
        Project project = findById(projectId);
        throwIfProjectIsNull(project);

        if (!project.getStudentResponsible().getId().equals(deliver.getStudentResponsible().getId())) {
            throw new StudentException.PostSolutionFailedException();
        }

        deliver.setStudents(project.getStudents());
        deliver.setStudentResponsible(project.getStudentResponsible());
        deliver.getProjects().add(project);

        Project returnProject = setSolution(projectId, deliver);;
        checkIfFieldIsNull(returnProject);
        return returnProject;
    }

//    private String generateCode() {
//        boolean unique = false;
//
//        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//        StringBuilder salt = new StringBuilder();
//        Random rnd = new Random();
//        String key = "";
//
//        while (!unique) {
//            while (salt.length() < 7) {
//                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//                salt.append(SALTCHARS.charAt(index));
//            }
//
//            key = salt.toString();
//
//            if (repository.findByAccessKey(key) == null) {
//                unique = true;
//            }
//        }
//
//        return key;
//    }

//    public Project StudentsEnterProjectByAccessKey(String accessKey, Long studentId) {
//        Project project = repository.findByAccessKey(accessKey);
//        throwIfProjectIsNull(project);
//
//        Student student = studentService.findById(studentId);
//        throwIfStudentIsNull(student, studentId);
//        throwIfStudentIsInactive(student);
//
//        project.getStudents().add(student);
//
//        return project;
//    }
//
//    public Project studentResponsibleEnterProjectByAccessKey(String accessKey, Long studentId) {
//        Project project = repository.findByAccessKey(accessKey);
//        throwIfProjectIsNull(project);
//
//        Student student = studentService.findById(studentId);
//        throwIfStudentIsNull(student, studentId);
//        throwIfStudentIsInactive(student);
//
//        project.setStudentResponsible(student);
//
//        return project;
//    }
//
//    public Project teacherEnterProjectByAccessKey(String accessKey, Long teacherId) {
//        Project project = repository.findByAccessKey(accessKey);
//        throwIfProjectIsNull(project);
//
//        Teacher teacher = teacherService.findById(teacherId);
//        throwIfTeacherIsNull(teacher, teacherId);
//        throwIfTeacherIsInactive(teacher);
//
//        project.setTeacher(teacher);
//
//        return project;
//    }
}
