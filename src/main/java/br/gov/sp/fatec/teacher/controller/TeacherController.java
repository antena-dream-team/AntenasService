package br.gov.sp.fatec.teacher.controller;

import br.gov.sp.fatec.teacher.controller.converter.TeacherConverter;
import br.gov.sp.fatec.teacher.controller.dto.TeacherDTO;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("dev/teacher")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @Autowired
    private TeacherConverter converter;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public TeacherDTO create (@RequestBody Teacher teacher) {
        return converter.toDTO(service.save(teacher));
    }
}

