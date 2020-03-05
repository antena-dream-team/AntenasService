package br.gov.sp.fatec.teacher.controller.converter;

import br.gov.sp.fatec.teacher.controller.dto.TeacherDTO;
import br.gov.sp.fatec.teacher.domain.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TeacherConverter {

    public Teacher toModel (TeacherDTO dto) {
        ModelMapper modelMapper = new ModelMapper();

        return  modelMapper.map(dto, Teacher.class);
    }

    public TeacherDTO toDTO (Teacher model) {
        ModelMapper modelMapper = new ModelMapper();

        return  modelMapper.map(model, TeacherDTO.class);
    }
}
