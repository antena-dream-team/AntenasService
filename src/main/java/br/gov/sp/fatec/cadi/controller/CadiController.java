package br.gov.sp.fatec.cadi.controller;

import br.gov.sp.fatec.cadi.controller.converter.CadiConverter;
import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.controller.dto.CadiDTO;
import br.gov.sp.fatec.cadi.service.CadiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/dev/cadi")
public class CadiController {

    @Autowired
    CadiService service;

    @Autowired
    CadiConverter converter;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    private CadiDTO create (@RequestBody Cadi cadi) {
        return converter.toDTO(service.save(cadi));
    }
}
