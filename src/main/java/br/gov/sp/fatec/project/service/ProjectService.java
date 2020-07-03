package br.gov.sp.fatec.project.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.service.EntrepreneurService;
import br.gov.sp.fatec.project.domain.Date;
import br.gov.sp.fatec.project.domain.Deliver;
import br.gov.sp.fatec.project.domain.Meeting;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.repository.ProjectRepository;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.exception.StudentException;
import br.gov.sp.fatec.student.service.StudentService;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.exception.TeacherException;
import br.gov.sp.fatec.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    @PreAuthorize("hasRole('ENTREPRENEUR')")
    public Project save(Project project) {
        if (project.getTeacher() != null && project.getTeacher().getId() != null) {
            Teacher found = teacherService.findById(project.getTeacher().getId());
            throwIfTeacherIsNull(found);
            throwIfTeacherIsInactive(found);
            project.setTeacher(found);
        }

        if (project.getEntrepreneur() != null) {
            Entrepreneur found = entrepreneurService.findById(project.getEntrepreneur().getId());
            throwIfEntrepreneurIsNull(found);
            throwIfEntrepreneurIsInactive(found);
            project.setEntrepreneur(found);
        }

        if (project.getStudents() != null) {
            List<Student> studentList = new LinkedList<>();
            for (Student student : project.getStudents()) {
                Student found = studentService.findById(student.getId());
                throwIfStudentIsNull(found);
                throwIfStudentIsInactive(found);
                studentList.add(found);
            }

            project.setStudents(studentList);
        }

        if (project.getStudentResponsible() != null && project.getStudentResponsible().getId() != null) {
            project.setStudentResponsible(studentService.findById(project.getStudentResponsible().getId()));
        }
        project.setCreatedAt(ZonedDateTime.now());
        project.setProgress(1);

        return repository.save(project);
    }

    @PreAuthorize("hasAnyRole('CADI', 'ENTREPRENEUR', 'STUDENT', 'TEACHER')")
    public List<Project> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasAnyRole('CADI', 'ENTREPRENEUR', 'STUDENT', 'TEACHER')")
    public Project findById(Long id) {
        Project project =  repository.findById(id).orElse(null);
        throwIfProjectIsNull(project, id);
        return project;
    }

    @PreAuthorize("hasRole('ENTREPRENEUR')")
    public void delete(Long id) {
        Project project = findById(id);
        throwIfProjectIsNull(project, id);

        repository.delete(project);
    }

    @PreAuthorize("hasRole('TEACHER')")
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

    @PreAuthorize("hasRole('TEACHER')")
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

        project.setStudents(students);
        return repository.save(project);
    }

    @PreAuthorize("hasRole('TEACHER')")
    public Project addStudent(Long projectId, Long studentId) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        Student student = studentService.findById(studentId);
        throwIfStudentIsNull(student, studentId);
        throwIfStudentIsInactive(student);

        project.getStudents().add(student);
        return repository.save(project);
    }

    @PreAuthorize("hasRole('CADI')")
    public Project setTeacher(Long projectId, Long teacherId) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        Teacher teacher = teacherService.findById(teacherId);
        throwIfTeacherIsNull(teacher, teacherId);
        throwIfTeacherIsInactive(teacher);

        project.setTeacher(teacher);

        return repository.save(project);
    }

    @PreAuthorize("hasRole('TEACHER')")
    public Project removeStudent(Long projectId, Long StudentId) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        Student student = studentService.findById(StudentId);
        throwIfStudentIsNull(student, StudentId);

        project.getStudents().remove(student);

        return repository.save(project);
    }

    public List<Project> getProjectByTeacher(Long teacherId) {
        return repository.findByTeacherId(teacherId);
    }

    public List<Project> getProjectByStudent(Long studentId) {
        return repository.findByStudentsId(studentId);
    }

    public List<Project> getProjectByStudentResponsible(Long studentId) {
        return repository.findByStudentResponsibleId(studentId);
    }

    public List<Project> getProjectByEntrepreneur(Long entrepreneurId) {
        return repository.findByEntrepreneurId(entrepreneurId);
    }

    @PreAuthorize("hasRole('STUDENT')")
    public Project setSolution(Long projectId, Deliver deliver) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project);

        project.setProgress(6);
        project.getDeliver().add(deliver);
        return repository.save(project);
    }

    @PreAuthorize("hasRole('ENTREPRENEUR')")
    public Project update(Project project) {
        Project found = findById(project.getId());
        throwIfProjectIsNull(found);

        found.setProgress(update_getProgress(found));
        found.setCompleteDescription(project.getCompleteDescription());
        found.setTechnologyDescription(project.getTechnologyDescription());
        found.setTitle(project.getTitle());
        found.setShortDescription(project.getShortDescription());
        found.setNotes(project.getNotes());

        return repository.save(found);
    }

    private int update_getProgress(Project project) {
        return project.getCompleteDescription() != null && project.getTechnologyDescription() != null && project.getProgress() == 2 ? 3 : project.getProgress();
    }

    @PreAuthorize("hasRole('CADI')")
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

    @PreAuthorize("hasRole('ENTREPRENEUR')")
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

    @PreAuthorize("hasRole('CADI')")
    public Project approve(Long id) {
        Project project = findById(id);
        throwIfProjectIsNull(project);

        project.setProgress(approve_getProgress(project));
        return repository.save(project);
    }

    public int approve_getProgress(Project project) {
        return project.getCompleteDescription() != null && project.getTechnologyDescription() != null ? 4 : 2;
    }

    public Map<String, List<Project>> findProjectByStudent(Long studentId) {

        Map<String, List<Project>> projects = new HashMap<>();
        projects.put("responsible", getProjectByStudentResponsible(studentId));
        projects.put("team", getProjectByStudent(studentId));

        return projects;
    }

    @PreAuthorize("hasRole('STUDENT')")
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

        return setSolution(projectId, deliver);
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
