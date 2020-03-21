package br.gov.sp.fatec.project.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.service.EntrepreneurService;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.domain.Status;
import br.gov.sp.fatec.project.repository.ProjectRepository;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.exception.StudentException.*;
import br.gov.sp.fatec.student.service.StudentService;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.service.TeacherService;
import br.gov.sp.fatec.utils.exception.InactiveException;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    public Project save(Project project) throws NotFoundException {
        if (project.getTeacher() != null && project.getTeacher().getId() != null) {
            Teacher found = teacherService.findById(project.getTeacher().getId());
            NotFoundException.throwIfNull(found);
            project.setTeacher(found);
        }

        if (project.getEntrepreneur() != null) {
            Entrepreneur found = entrepreneurService.findById(project.getEntrepreneur().getId());
            NotFoundException.throwIfNull(found);
            project.setEntrepreneur(found);
        }

        if (project.getStudents() != null) {
            Set<Long> studentList = new HashSet<>();

            for (Student student : project.getStudents()) {
                NotFoundException.throwIfNull(student.getId());
                studentList.add(student.getId());
            }

            project.setStudents(studentService.findAllById(studentList));
        }

        if (project.getStudentResponsible() != null && project.getStudentResponsible().getId() != null) {
            project.setStudentResponsible(studentService.findById(project.getStudentResponsible().getId()));
        }

        return repository.save(project);
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public Project findById(Long id) {
        return repository.findById(id).orElse(null);
    }

//    public void delete(Long id) { // todo - vai deletar ou desativar? se for desativar, é necessario adicionar o active no model (doman) e no banco (adicionar campo no arquivo dentro da pasta chengelog)
//
//    }

    public Project setStudentResponsible(Long projectId, Long studentId) throws NotFoundException, StudentInactiveException, StudentNotFoundException {
        Project project = findById(projectId);
        NotFoundException.throwIfProjectIsNull(project, projectId);

        Student student = studentService.findById(studentId);
        NotFoundException.throwIfStudentIsNull(student, studentId);
        InactiveException.throwIfStudentIsInactive(student);

        project.setStudentResponsible(student);
        return repository.save(project);
    }

    public Project setStudents(Long projectId, List<Student> studentList) throws StudentInactiveException, StudentNotFoundException {
        Project project = findById(projectId);
        NotFoundException.throwIfProjectIsNull(project, projectId);

        List<Student> students = new LinkedList<>();

        for (Student student : studentList) {
            Student found = studentService.findById(student.getId());
            NotFoundException.throwIfStudentIsNull(found, student.getId());
            InactiveException.throwIfStudentIsInactive(student);

            if (!found.isActive()) {
                throw new StudentInactiveException(student.getId());
            }

            students.add(found);
        }

        project.setStudents(students);
        return repository.save(project);
    }

    public Project setTeacher(Long projectId, Long teacherId) {
        Project project = findById(projectId);
        NotFoundException.throwIfProjectIsNull(project, projectId);

        Teacher teacher = teacherService.findById(teacherId);
        NotFoundException.throwIfTeacherIsNull(teacher, teacherId);

        project.setTeacher(teacher);

        return repository.save(project);
    }

    public Project setStatus(Long projectId, Status status) {
        Project project = findById(projectId);
        NotFoundException.throwIfProjectIsNull(project, projectId);

        project.setStatus(status);

        return repository.save(project);
    }
}
