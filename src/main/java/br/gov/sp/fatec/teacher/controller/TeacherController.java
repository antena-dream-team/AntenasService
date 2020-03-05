package br.gov.sp.fatec.teacher.controller;

import br.gov.sp.fatec.teacher.controller.converter.TeacherConverter;
import br.gov.sp.fatec.teacher.controller.dto.TeacherDTO;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/teacher")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @Autowired
    private TeacherConverter converter;

    @GetMapping
    @ResponseBody
    public TeacherDTO create (Teacher teacher) {
        return converter.toDTO(service.save(teacher));
    }
}

