package br.gov.sp.fatec.entrepreneur.controller.converter;

import br.gov.sp.fatec.entrepreneur.controller.dto.EntrepreneurDTO;
import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EntrepreneurConverter {

    public Entrepreneur toModel (EntrepreneurDTO dto) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(dto, Entrepreneur.class);
    }

    public EntrepreneurDTO toDTO (Entrepreneur model) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(model, EntrepreneurDTO.class);
    }

//    public Entrepreneur toModel(EntrepreneurDTO dto) {
//        Entrepreneur entrepreneur = new Entrepreneur();
//
//        entrepreneur.setEmail(dto.getEmail());
//        entrepreneur.setId(dto.getId());
//        entrepreneur.setPassword(dto.getPassword());
//
//        return entrepreneur;
//    }
//
//    public EntrepreneurDTO toDTO(Entrepreneur model) {
//        EntrepreneurDTO entrepreneur = new EntrepreneurDTO();
//
//        entrepreneur.setEmail(model.getEmail());
//        entrepreneur.setId(model.getId());
//        entrepreneur.setPassword(model.getPassword());
//
//        return entrepreneur;
//    }

}
