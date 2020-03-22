package br.gov.sp.fatec.cadi.controller;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.exception.CadiException;
import br.gov.sp.fatec.cadi.service.CadiService;
import br.gov.sp.fatec.cadi.view.CadiView;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.domain.Status;
import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.utils.commons.SendEmail;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/dev/cadi")
public class CadiController {

    @Autowired
    CadiService service;

    @Autowired
    private SendEmail sendEmail;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @JsonView(CadiView.Cadi.class)
    private Cadi create (@RequestBody Cadi cadi) {
        // todo - enviar verificação para o email
        sendEmail.sendMail();
        return (service.save(cadi));
    }

    @GetMapping(produces =  APPLICATION_JSON_VALUE)
    @JsonView(CadiView.Cadi.class)
    public List<Cadi> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @JsonView(CadiView.Cadi.class)
    public Cadi findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deactivate(@PathVariable("id") Long id) throws NotFoundException {
        service.deactivate(id);
    }

    @PutMapping(value = "/{id}")
    @JsonView(CadiView.Cadi.class)
    public Cadi update(@PathVariable("id") Long id,
                       @RequestBody Cadi cadi) {
        return  service.save(cadi);
    }

    @GetMapping(value = "/active")
    @JsonView(CadiView.Cadi.class)
    public List<Cadi> findActive() {
        return service.findActive();
    }

    @GetMapping(value = "/list-project")
    @JsonView(ProjectView.Project.class)
    public List<Project> getAllProjects() {
        return service.getAllProjects();
    }

    @PutMapping(value = "/set-teacher/{projectId}/{teacherId}")
    @JsonView(ProjectView.Project.class)
    public Project setTeacher(@PathVariable("teacherId") Long teacherId,
                              @PathVariable("projectId") Long projectId) {
        return service.setTeacher(teacherId, projectId);
    }

    @PutMapping(value = "/set-project-status/{projectId}")
    @JsonView(ProjectView.Project.class)
    public Project setStatus(@PathVariable("projectId") Long projectId,
                             @RequestBody Status status) {
        return service.setProjectStatus(projectId, status);
    }

    @PostMapping(value = "/login")
    @JsonView(CadiView.Cadi.class)
    public Cadi login(@RequestBody Map<String, Object> login) {
        try {
            String password = (String) login.get("password");
            String email = (String) login.get("email");
            return service.login(email, password);
        } catch (Exception e) {
            throw new CadiException.CadiLoginFailed();
        }
    }
}
