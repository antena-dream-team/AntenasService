package br.gov.sp.fatec.teacher.service;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.gov.sp.fatec.utils.exception.NotFoundException.throwIfTeacherIsNull;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;

    @Autowired
    private ProjectService projectService;

    public Teacher save(Teacher teacher) {
        return repository.save(teacher);
    }

    public Teacher deactivate(Long id) {
        Teacher found = repository.getOne(id);
        throwIfTeacherIsNull(found, id);

        found.setActive(false);
        repository.save(found);

        return found;
    }

    public List<Teacher> findAll() {
        return repository.findAll();
    }

    public List<Teacher> findActive() {
        return repository.findAllByActive(true);
    }

    public Teacher findById(Long id) {
        Teacher teacher = repository.getOne(id);
        throwIfTeacherIsNull(teacher, id);
        return teacher;
    }

    public Teacher update(Long id, Teacher teacher) {
        Teacher found = repository.getOne(id);
        throwIfTeacherIsNull(found, id);

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
