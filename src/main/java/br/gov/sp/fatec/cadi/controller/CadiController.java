package br.gov.sp.fatec.cadi.controller;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.service.CadiService;
import br.gov.sp.fatec.cadi.view.CadiView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/dev/cadi")
public class CadiController {

    @Autowired
    CadiService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @JsonView(CadiView.Cadi.class)
    private Cadi create (@RequestBody Cadi cadi) {
        return (service.save(cadi));
    }
}
