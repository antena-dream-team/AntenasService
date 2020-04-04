package br.gov.sp.fatec.teacher.service;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import br.gov.sp.fatec.utils.commons.SendEmail;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

import static br.gov.sp.fatec.utils.exception.InactiveException.throwIfTeacherIsInactive;
import static br.gov.sp.fatec.utils.exception.NotFoundException.throwIfTeacherIsNull;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;

    @Autowired
    private ProjectService projectService;

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

        found.setActive(teacher.getActive());
        found.setName(teacher.getName());
        found.setEmail(teacher.getEmail());

        return repository.save(found);
    }

    public Project setStudentsToProject(List<Student> studentList, Long projectId) {
        return projectService.setStudents(projectId, studentList);
    }

    public Project setStudentsResponsibleToProject(Long studentId, Long projectId){
            return projectService.setStudentResponsible(projectId, studentId);
    }

    public List<Project> listProjectByTeacher(Long teacherId) {
        return projectService.getProjectByTeacher(teacherId);
    }

    public Project removeStudent(Long projectId, Long studentId) {
        return projectService.removeStudents(projectId, studentId);
    }

    public Teacher login(String email, String password) {
        password =  Base64.getEncoder().encodeToString(password.getBytes());
        Teacher teacher = repository.findByEmailAndPassword(email, password);

        throwIfTeacherIsNull(teacher);
        throwIfTeacherIsInactive(teacher);

        return teacher;
    }

    public void activate(String b64) {
        JSONObject jsonObject = new JSONObject(new String(Base64.getDecoder().decode(b64)));
        Teacher found = repository.findByEmail(jsonObject.get("email").toString());
        throwIfTeacherIsNull(found);

        found.setActive(true);
        repository.save(found);
    }
}
