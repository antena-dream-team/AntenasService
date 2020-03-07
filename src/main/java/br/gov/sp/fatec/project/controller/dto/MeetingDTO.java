package br.gov.sp.fatec.project.controller.dto;

import br.gov.sp.fatec.project.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDTO {
    private Long id;
    private Date chosenDate;
    private Address address;
    private List<DateDTO> possible_date;
}