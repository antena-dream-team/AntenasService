package br.gov.sp.fatec.teacher.service;

import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;


    public Teacher save(Teacher teacher) {
        return repository.save(teacher);
    }

    public void delete(Long id) throws NotFoundException {
        NotFoundException.throwIfNull(repository.findById(id));
        repository.deleteById(id);
    }

    public List<Teacher> findAll() {
        return repository.findAll();
    }

    public Teacher findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Teacher update(Long id, Teacher teacher) throws NotFoundException {
        Teacher found = repository.findById(id).orElse(null);
        NotFoundException.throwIfNull(found);

        found.setActive(teacher.getActive());
        found.setName(teacher.getName());
        found.setEmail(teacher.getEmail());

        return repository.save(found);

    }

}
