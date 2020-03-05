package br.gov.sp.fatec.cadi.controller.converter;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.controller.dto.CadiDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CadiConverter {

    public Cadi toModel (CadiDTO dto) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(dto, Cadi.class);
    }

    public CadiDTO toDTO (Cadi model) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(model, CadiDTO.class);
    }

//    public Cadi toModel (CadiDTO dto) {
//        Cadi cadi =  new Cadi();
//        cadi.setEmail(dto.getEmail());
//        cadi.setPassword(dto.getPassword());
//
//        return cadi;
//    }
//
//    public CadiDTO toDTO (Cadi cadi) {
//        CadiDTO cadiDTO = new CadiDTO();
//        cadiDTO.setEmail(cadi.getEmail());
//        cadiDTO.setPassword(cadi.getPassword());
//
//        return cadiDTO;
//    }
}
