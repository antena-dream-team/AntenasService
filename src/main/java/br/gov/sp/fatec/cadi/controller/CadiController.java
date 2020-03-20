package br.gov.sp.fatec.cadi.controller;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.service.CadiService;
import br.gov.sp.fatec.cadi.view.CadiView;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/dev/cadi")
public class CadiController {

    @Autowired
    CadiService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @JsonView(CadiView.Cadi.class)
    private Cadi create (@RequestBody Cadi cadi) {
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
}
