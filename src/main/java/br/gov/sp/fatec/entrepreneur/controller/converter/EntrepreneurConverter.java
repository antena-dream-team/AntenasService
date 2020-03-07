package br.gov.sp.fatec.entrepreneur.controller.converter;

import br.gov.sp.fatec.entrepreneur.controller.dto.EntrepreneurDTO;
import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import org.springframework.stereotype.Service;

@Service
public class EntrepreneurConverter {

    public Entrepreneur toModel(EntrepreneurDTO dto) {
        Entrepreneur model = new Entrepreneur();

        model.setId(dto.getId());
        model.setPassword(dto.getPassword());
        model.setActive(dto.isActive());
        model.setCpf(dto.getCpf());
        model.setName(dto.getTelephone());
        model.setEmail(dto.getEmail());
        model.setCompany(dto.getCompany());
        model.setTelephone(dto.getTelephone());

        return model;
    }

    public EntrepreneurDTO toDTO(Entrepreneur model) {
        EntrepreneurDTO dto = new EntrepreneurDTO();

        dto.setEmail(model.getEmail());
        dto.setId(model.getId());
        dto.setPassword(model.getPassword());
        dto.setActive(model.isActive());
        dto.setCpf(model.getCpf());
        dto.setName(model.getTelephone());
        dto.setCompany(model.getCompany());
        dto.setTelephone(model.getTelephone());

        return dto;
    }
}
