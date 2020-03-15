package br.gov.sp.fatec.student.controller;

import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.service.StudentService;
import br.gov.sp.fatec.student.view.StudentView;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("dev/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @JsonView(StudentView.Student.class)
    public Student create (@RequestBody Student student) {
        return service.save(student);
    }

    @GetMapping(produces =  APPLICATION_JSON_VALUE)
    @JsonView(StudentView.Student.class)
    public List<Student> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @JsonView(StudentView.Student.class)
    public Student findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deactivate(@PathVariable("id") Long id) throws NotFoundException {
        service.deactivate(id);
    }

    @PutMapping(value = "/{id}")
    @JsonView(StudentView.Student.class)
    public Student update(@PathVariable("id") Long id,
                          @RequestBody Student student) {
        return  service.save(student);
    }

    @GetMapping(value = "/active")
    @JsonView(StudentView.Student.class)
    public List<Student> findActive() {
        return service.findActive();
    }
}
