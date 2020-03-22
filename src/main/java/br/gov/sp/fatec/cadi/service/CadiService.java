package br.gov.sp.fatec.cadi.service;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.repository.CadiRepository;
import br.gov.sp.fatec.cadi.exception.CadiException.CadiNotFoundException;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.domain.Status;
import br.gov.sp.fatec.project.service.ProjectService;
import br.gov.sp.fatec.utils.commons.JSONParser;
import br.gov.sp.fatec.utils.commons.SendEmail;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

import static br.gov.sp.fatec.utils.exception.InactiveException.throwIfCadiIsInactive;
import static br.gov.sp.fatec.utils.exception.NotFoundException.throwIfCadiIsNull;

@Service
public class CadiService {

    @Autowired
    private CadiRepository repository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SendEmail sendEmail;

    public Cadi save(Cadi cadi) {
        cadi.setActive(false);
        cadi.setPassword(Base64.getEncoder().encodeToString(cadi.getPassword().getBytes()));
        sendEmail.sendMail(cadi.getEmail(), "cadi");
        return repository.save(cadi);
    }

    public List<Cadi> findAll() {
        return repository.findAll();
    }

    public List<Cadi> findActive() {
        return repository.findAllByActive(true);
    }

    public Cadi findById(Long id) {
        Cadi found = repository.findById(id).orElse(null);
        throwIfCadiIsNull(found, id);
        return found;
    }

    public Cadi update(Long id, Cadi cadi) {
        Cadi found = repository.findById(id).orElse(null);
        throwIfCadiIsNull(found, id);

        found.setActive(cadi.isActive());
        found.setName(cadi.getName());
        found.setEmail(cadi.getEmail());
        found.setCpf(cadi.getCpf());
        found.setPosition(cadi.getPosition());

        return repository.save(found);
    }

    public Cadi deactivate(Long id) {
        Cadi found = repository.getOne(id);
        throwIfCadiIsNull(found, id);

        found.setActive(false);
        repository.save(found);

        return found;
    }

    public void activate(String b64) {
        JSONObject jsonObject = new JSONObject(new String(Base64.getDecoder().decode(b64)));
        Cadi found = repository.findByEmail(jsonObject.get("email").toString());
        throwIfCadiIsNull(found);

        found.setActive(true);
        repository.save(found);
    }

    public List<Project> getAllProjects() {
        return projectService.findAll();
    }

    public Project setTeacher(Long teacherId, Long projectId) {
        return projectService.setTeacher(teacherId, projectId);
    }

    public Project setProjectStatus(Long projectId, Status status) {
        return projectService.setStatus(projectId, status);
    }

    public Cadi login(String email, String password) {
        password =  Base64.getEncoder().encodeToString(password.getBytes());
        Cadi cadi = repository.findByEmailAndPassword(email, password);

        throwIfCadiIsNull(cadi);
        throwIfCadiIsInactive(cadi);

        return cadi;
    }
}
