package br.gov.sp.fatec.entrepreneur.controller;

import br.gov.sp.fatec.entrepreneur.controller.converter.EntrepreneurConverter;
import br.gov.sp.fatec.entrepreneur.controller.dto.EntrepreneurDTO;
import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.service.EntrepreneurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("dev/entrepreneur")
public class EntrepreneurController {

    @Autowired
    EntrepreneurService service;

    @Autowired
    EntrepreneurConverter converter;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public EntrepreneurDTO create (@RequestBody Entrepreneur entrepreneur) {
        return converter.toDTO(service.save(entrepreneur));
    }
}