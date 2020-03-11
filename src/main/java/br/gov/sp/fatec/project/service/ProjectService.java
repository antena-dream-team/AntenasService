package br.gov.sp.fatec.project.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.repository.EntrepreneurRepository;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.repository.ProjectRepository;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.repository.StudentRepository;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    public Project save(Project project) {
        // TODO  - ADD EXCEPTION CASO O PROFESSOR, EMPRESARIO OU ALUNO NÃO EXISTAM NO BANCO
        if (project.getTeacher() != null && project.getTeacher().getId() != null) {
            project.setTeacher(teacherRepository.findById(project.getTeacher().getId()).get());
        }

        if (project.getEntrepreneur() != null) {
            project.setEntrepreneur(entrepreneurRepository.findById(project.getEntrepreneur().getId()).get());
        }

        if (project.getStudents() != null) {
            Set<Long> studentList = new HashSet<>();

            for (Student student : project.getStudents()) {
                if (student.getId() != null) {
                    studentList.add(student.getId());
                }
            }

            project.setStudents(studentRepository.findAllById(studentList));

        }

        if (project.getStudentResponsible() != null && project.getStudentResponsible().getId() != null) {
            project.setStudentResponsible(studentRepository.getOne(project.getStudentResponsible().getId()));
        }

        return repository.save(project);
    }

    public void delete(Long id) {
        Optional<Project> project = repository.findById(id);
        // TODO: NotFoundException - check if exists
        repository.deleteById(id);
    }
}
