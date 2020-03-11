package br.gov.sp.fatec.project.controller;

import br.gov.sp.fatec.project.service.ProjectService;
import br.gov.sp.fatec.project.view.ProjectView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("dev/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @JsonView(ProjectView.Project.class)
    public br.gov.sp.fatec.project.domain.Project create (@RequestBody br.gov.sp.fatec.project.domain.Project project) {

        return service.save(project);
    }
}
