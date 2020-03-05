package br.gov.sp.fatec.student.controller.converter;

import br.gov.sp.fatec.student.controller.dto.StudentDTO;
import br.gov.sp.fatec.student.domain.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentConverter {

    public Student toModel (StudentDTO dto) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(dto, Student.class);
    }

    public StudentDTO toDTO (Student model) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(model, StudentDTO.class);
    }
}
