package br.gov.sp.fatec.entrepreneur.controller;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.service.EntrepreneurService;
import br.gov.sp.fatec.entrepreneur.view.EntrepreneurView;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("dev/entrepreneur")
public class EntrepreneurController {

    @Autowired
    EntrepreneurService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
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
    public void deactivate(@PathVariable("id") Long id) throws NotFoundException {
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
}
