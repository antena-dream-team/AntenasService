package br.gov.sp.fatec.teacher.service;

import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;


    public Teacher save(Teacher teacher) {
        return repository.save(teacher);
    }

    public void delete(Long id) {
        Optional<Teacher> teacher = repository.findById(id);
        // TODO: NotFoundException - check if exists
        repository.deleteById(id);
    }
}
