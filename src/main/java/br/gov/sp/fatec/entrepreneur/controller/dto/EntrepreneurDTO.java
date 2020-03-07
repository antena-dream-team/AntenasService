package br.gov.sp.fatec.entrepreneur.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrepreneurDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String cpf;
    private String telephone;
    private boolean active;
    private String company;
}
