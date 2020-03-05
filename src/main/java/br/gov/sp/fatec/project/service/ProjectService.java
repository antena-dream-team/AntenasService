package br.gov.sp.fatec.project.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository repository;

    public Project save(Project project) {
        return repository.save(project);
    }

    public void delete(Long id) {
        Optional<Project> project = repository.findById(id);
        // TODO: NotFoundException - check if exists
        repository.deleteById(id);
    }
}
