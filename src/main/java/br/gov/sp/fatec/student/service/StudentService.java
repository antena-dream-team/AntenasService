package br.gov.sp.fatec.student.service;

import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.repository.StudentRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public Student save(Student student) {
        return repository.save(student);
    }

    public void deactivate(Long id) throws NotFoundException {
        Student found = repository.findById(id).orElse(null);
        NotFoundException.throwIfNull(found);

        found.setActive(false);
        repository.save(found);
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public List<Student> findActive() {
        return repository.findAllByActive(true);
    }

    public Student findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Student update(Long id, Student student) throws NotFoundException {
        Student found = repository.findById(id).orElse(null);
        NotFoundException.throwIfNull(repository.findById(id));

        found.setName(student.getName());
        found.setEmail(student.getName());
        found.setActive(student.isActive());
        found.setProjects(student.getProjects());

        return found;

    }
}
