package br.gov.sp.fatec.teacher.service;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import br.gov.sp.fatec.student.exception.StudentException.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;

    @Autowired
    private ProjectService projectService;

    public Teacher save(Teacher teacher) {
        return repository.save(teacher);
    }

    public void deactivate(Long id) throws NotFoundException {
        Teacher found = repository.findById(id).orElse(null);
        NotFoundException.throwIfNull(found);

        found.setActive(false);
        repository.save(found);
    }

    public List<Teacher> findAll() {
        return repository.findAll();
    }

    public List<Teacher> findActive() {
        return repository.findAllByActive(true);
    }

    public Teacher findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Teacher update(Long id, Teacher teacher) throws NotFoundException {
        Teacher found = repository.findById(id).orElse(null);
        NotFoundException.throwIfNull(found);

        found.setActive(teacher.getActive());
        found.setName(teacher.getName());
        found.setEmail(teacher.getEmail());

        return repository.save(found);
    }

    public Project setStudentsToProject(List<Student> studentList, Long projectId) {
        try {
            return projectService.setStudents(projectId, studentList);
        } catch (Exception | NotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Project setStudentsResponsibleToProject(Long studentId, Long projectId){
        try {
        return projectService.setStudentResponsible(projectId, studentId);
        } catch (Exception | NotFoundException ex ) {
            ex.printStackTrace();
        }

        return null;
    }
}
