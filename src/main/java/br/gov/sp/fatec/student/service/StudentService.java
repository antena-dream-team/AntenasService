package br.gov.sp.fatec.student.service;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public Student save(Student student) {
        return repository.save(student);
    }

    public void delete(Long id) {
        Optional<Student> student = repository.findById(id);
        // TODO: NotFoundException - check if exists
        repository.deleteById(id);
    }
}
