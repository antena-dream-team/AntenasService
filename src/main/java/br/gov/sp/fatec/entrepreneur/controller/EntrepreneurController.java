package br.gov.sp.fatec.entrepreneur.controller;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.exception.EntrepreneurException.*;
import br.gov.sp.fatec.entrepreneur.service.EntrepreneurService;
import br.gov.sp.fatec.entrepreneur.view.EntrepreneurView;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("dev/entrepreneur")
public class EntrepreneurController {

    @Autowired
    EntrepreneurService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    @JsonView(EntrepreneurView.Entrepreneur.class)
    public Entrepreneur create (@RequestBody Entrepreneur entrepreneur) {
        return service.save(entrepreneur);
    }

    @GetMapping(produces =  APPLICATION_JSON_VALUE)
    @JsonView(EntrepreneurView.Entrepreneur.class)
    public List<Entrepreneur> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @JsonView(EntrepreneurView.Entrepreneur.class)
    public Entrepreneur findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deactivate(@PathVariable("id") Long id) {
        // todo - remover do projeto se desativar
        service.deactivate(id);
    }

    @PutMapping(value = "/{id}")
    @JsonView(EntrepreneurView.Entrepreneur.class)
    public Entrepreneur update(@PathVariable("id") Long id,
                               @RequestBody Entrepreneur entrepreneur) {
        return  service.save(entrepreneur);
    }

    @GetMapping(value = "/active")
    @JsonView(EntrepreneurView.Entrepreneur.class)
    public List<Entrepreneur> findActive() {
        return service.findActive();
    }

    @PostMapping(value = "/login")
    @JsonView(EntrepreneurView.Entrepreneur.class)
    public Entrepreneur login(@RequestBody Map<String, String> login) {
        return service.login(login);
    }

    @GetMapping(value = "/get-project/{id}")
    @JsonView(ProjectView.Project.class)
    public List<Project> getProjectByEntrepreneur(@PathVariable("id") Long id) {
        return service.getProjectByEntrepreneur(id);
    }

    @PostMapping(value = "/set-chosen-date/{projectId}/{dateId}")
    @JsonView(ProjectView.Project.class)
    public Project setMeetingChosenDate(@PathVariable("dateId") Long dateId,
                                        @PathVariable("projectId") Long projectId) {
        return service.setMeetingChosenDate(dateId, projectId);
    }
}
