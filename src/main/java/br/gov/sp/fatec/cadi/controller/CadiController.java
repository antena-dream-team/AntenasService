package br.gov.sp.fatec.cadi.controller;

import br.gov.sp.fatec.cadi.controller.converter.CadiConverter;
import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.controller.dto.CadiDTO;
import br.gov.sp.fatec.cadi.service.CadiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dev/cadi")
public class CadiController {

    @Autowired
    CadiService service;

    @Autowired
    CadiConverter converter;

    @PostMapping
    @ResponseBody
    private CadiDTO create (Cadi cadi) {
        return converter.toDTO(service.save(cadi));
    }

}
