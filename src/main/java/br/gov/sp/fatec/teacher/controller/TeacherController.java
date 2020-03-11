package br.gov.sp.fatec.teacher.controller;

import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.service.TeacherService;
import br.gov.sp.fatec.teacher.view.TeacherView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

