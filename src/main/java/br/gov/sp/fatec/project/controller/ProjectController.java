package br.gov.sp.fatec.project.controller;

import br.gov.sp.fatec.project.controller.converter.ProjectConverter;
import br.gov.sp.fatec.project.controller.dto.ProjectDTO;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private ProjectConverter converter;

    @PostMapping
    @ResponseBody
    public ProjectDTO create (Project project) {
        return converter.toDTO(service.save(project));
    }

}
