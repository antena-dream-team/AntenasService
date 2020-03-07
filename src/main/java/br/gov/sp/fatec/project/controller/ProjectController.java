package br.gov.sp.fatec.project.controller;

import br.gov.sp.fatec.project.controller.converter.ProjectConverter;
import br.gov.sp.fatec.project.controller.dto.ProjectDTO;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("dev/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private ProjectConverter converter;


//    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ProjectDTO create (@RequestBody Project project) {
//
//        return converter.toDTO(service.save(project));
//    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String create (@RequestBody Project project) {

        return "Não implementado";
    }

}
