package br.gov.sp.fatec.teacher.controller;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.service.TeacherService;
import br.gov.sp.fatec.teacher.view.TeacherView;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import br.gov.sp.fatec.student.exception.StudentException.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("dev/teacher")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @JsonView(TeacherView.Teacher.class)
    public Teacher create (@RequestBody Teacher teacher) {
        return service.save(teacher);
    }

    @GetMapping(produces =  APPLICATION_JSON_VALUE)
    @JsonView(TeacherView.Teacher.class)
    public List<Teacher> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/active")
    @JsonView(TeacherView.Teacher.class)
    public List<Teacher> findActive() {
        return service.findActive();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @JsonView(TeacherView.Teacher.class)
    public Teacher findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deactivate(@PathVariable("id") Long id) throws NotFoundException {
        service.deactivate(id);
    }

    @PutMapping(value = "/{id}")
    @JsonView(TeacherView.Teacher.class)
    public Teacher update(@PathVariable("id") Long id,
                   @RequestBody Teacher teacher) {
        return  service.save(teacher);
    }

    @PostMapping(value = "/set-students/{projectId}")
    @JsonView(ProjectView.Project.class)
    public Project setStudents(@PathVariable("projectId") Long projectId,
                                         @RequestBody List<Student> studentList) {

        // todo - checar se o professor pode adicionar os alunos
        // todo - se ja ouverem alunos, sobrescrever ou so adicionar mais? depende de como vai funcionar o front. está sobrescrevendo

        return service.setStudentsToProject(studentList, projectId);
    }

    @PostMapping(value = "/set-student-responsible/{projectId}/{studentId}")
    @JsonView(ProjectView.Project.class)
    public Project setResponsibleStudent(@PathVariable("projectId") Long projectId,
                                         @PathVariable("studentId") Long studentId) {

        // todo - checar se o professor pode adicionar o aluno
        // todo - fazer com que o projeto fique na lista de projetos do aluno ???

        return service.setStudentsResponsibleToProject(studentId, projectId);
    }

    @GetMapping(value = "/list-project-by-teacher/{teacherId}")
    @JsonView(ProjectView.Project.class)
    public List<Project> listProjectByTeacher(@PathVariable("teacherId") Long teacherId) {
        return service.listProjectByTeacher(teacherId);
    }

    @DeleteMapping(value = "/remove-student/{projectId}/{studentId}")
    @JsonView(ProjectView.Project.class)
    public Project removeStudent(@PathVariable("projectId") Long projectId,
                                 @PathVariable("studentId") Long studentId) {
        return service.removeStudents(projectId, studentId);
    }
}

