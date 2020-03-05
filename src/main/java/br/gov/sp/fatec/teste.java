package br.gov.sp.fatec;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/teste")
public class teste {

    @GetMapping
    @ResponseBody
    public String execute() {
        System.out.println("Hello world");
        return "Hello world";
    }
}