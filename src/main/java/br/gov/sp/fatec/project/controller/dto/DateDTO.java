package br.gov.sp.fatec.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateDTO {
    private Long id;
    private Date dateTime;
}
