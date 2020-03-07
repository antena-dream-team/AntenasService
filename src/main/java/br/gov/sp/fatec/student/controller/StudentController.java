package br.gov.sp.fatec.student.controller;

import br.gov.sp.fatec.student.controller.converter.StudentConverter;
import br.gov.sp.fatec.student.controller.dto.StudentDTO;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("dev/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @Autowired
    private StudentConverter converter;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public StudentDTO create (@RequestBody Student student) {
        return converter.toDTO(service.save(student));
    }
}
