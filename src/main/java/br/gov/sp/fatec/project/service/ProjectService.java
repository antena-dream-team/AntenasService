package br.gov.sp.fatec.project.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.repository.EntrepreneurRepository;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.repository.ProjectRepository;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EntrepreneurRepository entrepreneurRepository;

    public Project save(Project project) {
        // TODO  - ADD EXCEPTION CASO O PROFESSOR OU EMPRESARIO NÃO EXISTAM NO BANCO
        if (project.getTeacher() != null) {
            project.setTeacher(teacherRepository.findById(project.getTeacher().getId()).get());

        }

        if (project.getEntrepreneur() != null) {
            project.setEntrepreneur(entrepreneurRepository.findById(project.getEntrepreneur().getId()).get());
        }

        return repository.save(project);
    }

    public void delete(Long id) {
        Optional<Project> project = repository.findById(id);
        // TODO: NotFoundException - check if exists
        repository.deleteById(id);
    }
}
