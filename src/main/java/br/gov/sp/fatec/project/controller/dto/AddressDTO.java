package br.gov.sp.fatec.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;
    private String place;
    private int number;
    private String street;
    private String neighborhood;
    private String city;
    private String zip;
    private List<MeetingDTO> meeting = new ArrayList<>();
}
