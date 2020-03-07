package br.gov.sp.fatec.student.controller.converter;

import br.gov.sp.fatec.student.controller.dto.StudentDTO;
import br.gov.sp.fatec.student.domain.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentConverter {

    public Student toModel(StudentDTO dto) {
        Student model = new Student();

        model.setActive(dto.isActive());
        model.setEmail(dto.getEmail());
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setPassword(dto.getPassword());

        return model;
    }

    public StudentDTO toDTO(Student model) {
        StudentDTO dto = new StudentDTO();

        dto.setActive(model.isActive());
        dto.setEmail(model.getEmail());
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setPassword(model.getPassword());

        return dto;
    }

}
