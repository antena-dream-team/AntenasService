package br.gov.sp.fatec.teacher.service;

import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import br.gov.sp.fatec.utils.commons.SendEmail;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.List;

import static br.gov.sp.fatec.utils.exception.InactiveException.throwIfTeacherIsInactive;
import static br.gov.sp.fatec.utils.exception.NotFoundException.throwIfTeacherIsNull;

@Service
@Transactional
public class TeacherService {

    @Autowired
    private TeacherRepository repository;

    @Autowired
    private SendEmail sendEmail;

    public Teacher save(Teacher teacher) {
        teacher.setActive(false);
        teacher.setPassword(Base64.getEncoder().encodeToString(teacher.getPassword().getBytes()));
        sendEmail.sendMail(teacher.getEmail(), "teacher");

        return repository.save(teacher);
    }

    public List<Teacher> findAll() {
        return repository.findAll();
    }

    public List<Teacher> findActive() {
        return repository.findAllByActive(true);
    }

    public Teacher findById(Long id) {
        Teacher teacher = repository.getOne(id);
        throwIfTeacherIsNull(teacher, id);
        return teacher;
    }

    public Teacher update(Long id, Teacher teacher) {
        Teacher found = repository.getOne(id);
        throwIfTeacherIsNull(found, id);

        found.setActive(teacher.isActive());
        found.setName(teacher.getName());
        found.setEmail(teacher.getEmail());

        return repository.save(found);
    }

    public Teacher login(String email, String password) {
        password =  Base64.getEncoder().encodeToString(password.getBytes());
        Teacher teacher = repository.findByEmailAndPassword(email, password);

        throwIfTeacherIsNull(teacher);
        throwIfTeacherIsInactive(teacher);

        return teacher;
    }

    public void activate(String b64) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new String(Base64.getDecoder().decode(b64)));
            Teacher found = repository.findByEmail(jsonObject.get("email").toString());
            throwIfTeacherIsNull(found);

            found.setActive(true);
            repository.save(found);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
