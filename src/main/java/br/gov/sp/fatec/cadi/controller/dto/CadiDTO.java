package br.gov.sp.fatec.cadi.controller.dto;

import lombok.Data;

@Data
public class CadiDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String cpf;
    private String position;
    private boolean active;
}
