package br.gov.sp.fatec.project.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.service.EntrepreneurService;
import br.gov.sp.fatec.project.domain.*;
import br.gov.sp.fatec.project.repository.ProjectRepository;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.service.StudentService;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import static br.gov.sp.fatec.utils.exception.InactiveException.*;
import static br.gov.sp.fatec.utils.exception.NotFoundException.*;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private EntrepreneurService entrepreneurService;

    @Autowired
    private StudentService studentService;

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
//        project.setAccessKey(generateCode());
        project.setCreatedAt(ZonedDateTime.now());

        return repository.save(project);
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public Project findById(Long id) {
        return repository.getOne(id);
    }

    public void delete(Long id) {
        Project project = findById(id);
        throwIfProjectIsNull(project, id);

        repository.delete(project);
    }

    public Project setStudentResponsible(Long projectId, Long studentId) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        Student student = studentService.findById(studentId);
        throwIfStudentIsNull(student, studentId);
        throwIfStudentIsInactive(student);

        project.setStudentResponsible(student);
        return repository.save(project);
    }

    // serve para editar também. ele sobrescreve. Só deve ser passado todos os alunos
    public Project setStudents(Long projectId, List<Student> studentList) {
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

    public Project setTeacher(Long projectId, Long teacherId) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        Teacher teacher = teacherService.findById(teacherId);
        throwIfTeacherIsNull(teacher, teacherId);
        throwIfTeacherIsInactive(teacher);

        project.setTeacher(teacher);

        return repository.save(project);
    }

    public Project setStatus(Long projectId, Status status) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project, projectId);

        project.setStatus(status);

        return repository.save(project);
    }

    public Project removeStudents(Long projectId, Long StudentId) {
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

    public List<Project> getProjectByEntrepreneur(Long entrepreneurId) {
        return repository.findByEntrepreneurId(entrepreneurId);
    }

    public Project setSolution(Long projectId, Deliver deliver) {
        Project project = findById(projectId);
        throwIfProjectIsNull(project);


        project.getDeliver().add(deliver);
        return repository.save(project);
    }

    public Project setMeetingPossibleDate(List<Date> possibleDate, Long projectId) {
        for (Date date : possibleDate) {
            throwIfDateIsNull(date);
        }

        Project project = findById(projectId);
        throwIfProjectIsNull(project);

        Meeting meeting = Meeting.builder()
                .possibleDate(possibleDate)
                .build();

        project.setMeeting(meeting);

        return repository.save(project);
    }

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
