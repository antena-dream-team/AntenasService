package br.gov.sp.fatec.teacher.controller.converter;

import br.gov.sp.fatec.teacher.controller.dto.TeacherDTO;
import br.gov.sp.fatec.teacher.domain.Teacher;
import org.springframework.stereotype.Service;

@Service
public class TeacherConverter {

    public Teacher toModel(TeacherDTO dto) {
        Teacher model = new Teacher();

        model.setEmail(dto.getEmail());
        model.setId(dto.getId());
        model.setPassword(dto.getPassword());
        model.setName(dto.getName());
        model.setActive(dto.getActive());
//        model.setPosition(dto.getPosition());

        return model;
    }


    public TeacherDTO toDTO(Teacher model) {
        TeacherDTO dto = new TeacherDTO();

        dto.setEmail(model.getEmail());
        dto.setId(model.getId());
        dto.setPassword(model.getPassword());
        dto.setActive(model.getActive());
        dto.setName(model.getName());
//        dto.setPosition(dto.getPosition());

        return dto;
    }
}
