package br.gov.sp.fatec.project.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.repository.EntrepreneurRepository;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.repository.ProjectRepository;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.repository.StudentRepository;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EntrepreneurRepository entrepreneurRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Project save(Project project) throws NotFoundException {
        if (project.getTeacher() != null && project.getTeacher().getId() != null) {
            Teacher found = teacherRepository.findById(project.getTeacher().getId()).orElse(null);
            NotFoundException.throwIfNull(found);
            project.setTeacher(found);
        }

        if (project.getEntrepreneur() != null) {
            Entrepreneur found = entrepreneurRepository.findById(project.getEntrepreneur().getId()).orElse(null);
            NotFoundException.throwIfNull(found);
            project.setEntrepreneur(found);
        }

        if (project.getStudents() != null) {
            Set<Long> studentList = new HashSet<>();

            for (Student student : project.getStudents()) {
                NotFoundException.throwIfNull(student.getId());
                studentList.add(student.getId());
            }

            project.setStudents(studentRepository.findAllById(studentList));
        }

        if (project.getStudentResponsible() != null && project.getStudentResponsible().getId() != null) {
            project.setStudentResponsible(studentRepository.getOne(project.getStudentResponsible().getId()));
        }

        return repository.save(project);
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public Project findById(Long id) {
        return repository.findById(id).orElse(null);
    }

//    public void delete(Long id) { // todo - vai deletar ou desativar? se for desativar, é necessario adicionar o active no model (doman) e no banco (adicionar campo no arquivo dentro da pasta chengelog)
//
//    }
}
