package br.gov.sp.fatec.project.controller.converter;

import br.gov.sp.fatec.project.controller.dto.ProjectDTO;
import br.gov.sp.fatec.project.domain.Project;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProjectConverter {

    public Project toModel (ProjectDTO dto) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(dto, Project.class);
    }

    public ProjectDTO toDTO (Project model) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(model, ProjectDTO.class);
    }

}
