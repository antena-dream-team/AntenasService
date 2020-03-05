package br.gov.sp.fatec.student.controller;

import br.gov.sp.fatec.student.controller.converter.StudentConverter;
import br.gov.sp.fatec.student.controller.dto.StudentDTO;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @Autowired
    private StudentConverter converter;

    @GetMapping
    @ResponseBody
    public StudentDTO create (Student student) {
        return converter.toDTO(service.save(student));
    }
}
