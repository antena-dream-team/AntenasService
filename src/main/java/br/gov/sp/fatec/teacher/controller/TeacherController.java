package br.gov.sp.fatec.teacher.controller;

import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.service.TeacherService;
import br.gov.sp.fatec.teacher.view.TeacherView;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("dev/teacher")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @JsonView(TeacherView.Teacher.class)
    public Teacher create (@RequestBody Teacher teacher) {
        return service.save(teacher);
    }

    @GetMapping(produces =  APPLICATION_JSON_VALUE)
    @JsonView(TeacherView.Teacher.class)
    public List<Teacher> findAll() {
        return service.findAll();
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

    @GetMapping(value = "/active")
    @JsonView(TeacherView.Teacher.class)
    public List<Teacher> findActive() {
        return service.findActive();
    }
}

