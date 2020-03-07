package br.gov.sp.fatec.cadi.controller.converter;

import br.gov.sp.fatec.cadi.controller.dto.CadiDTO;
import br.gov.sp.fatec.cadi.domain.Cadi;
import org.springframework.stereotype.Service;

@Service
public class CadiConverter {

    public Cadi toModel (CadiDTO dto) {
        Cadi model = new Cadi();

        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());
        model.setActive(dto.isActive());
        model.setId(dto.getId());
        model.setPosition(dto.getPosition());

        return model;
    }

    public CadiDTO toDTO (Cadi model) {
        CadiDTO dto = new CadiDTO();

        dto.setEmail(model.getEmail());
        dto.setPassword(model.getPassword());
        dto.setCpf(model.getCpf());
        dto.setName(model.getName());
        dto.setCpf(model.getCpf());

        return dto;
    }
}
