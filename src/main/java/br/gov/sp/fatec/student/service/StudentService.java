package br.gov.sp.fatec.student.service;

import br.gov.sp.fatec.project.domain.Deliver;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.exception.StudentException.PostSolutionFailedException;
import br.gov.sp.fatec.student.repository.StudentRepository;
import br.gov.sp.fatec.utils.commons.SendEmail;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.gov.sp.fatec.utils.exception.InactiveException.throwIfStudentIsInactive;
import static br.gov.sp.fatec.utils.exception.NotFoundException.throwIfProjectIsNull;
import static br.gov.sp.fatec.utils.exception.NotFoundException.throwIfStudentIsNull;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SendEmail sendEmail;

    public Student save(Student student) {
        student.setActive(false);
        student.setPassword(Base64.getEncoder().encodeToString(student.getPassword().getBytes()));
        sendEmail.sendMail(student.getEmail(), "student");
        return repository.save(student);
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public List<Student> findAllById(List<Long> idList) {
        return repository.findAllById(idList);
    }

    public List<Student> findActive() {
        return repository.findAllByActive(true);
    }

    public Student findById(Long id) {
        Student found = repository.getOne(id);
        NotFoundException.throwIfStudentIsNull(found, id);

//        found.setProjects(findProjectByStudent(id));
//        System.out.println(found);
        return found;
    }

    public Student update(Long id, Student student) {
        Student found = repository.findById(id).orElse(null);
        NotFoundException.throwIfStudentIsNull(found, id);

        found.setName(student.getName());
        found.setEmail(student.getEmail());
        found.setActive(student.isActive());
        found.setProjects(student.getProjects());

        return found;
    }

    public Map<String, List<Project>>  findProjectByStudent(Long studentId) {

        Map<String, List<Project>> projects = new HashMap<>();
        projects.put("responsible", projectService.getProjectByStudentResponsible(studentId));
        projects.put("team", projectService.getProjectByStudent(studentId));

        return projects;
    }

    public Project setSolution(Deliver deliver, Long projectId) {
        // todo - pegar id do aluno responsavel
        Project project = projectService.findById(projectId);
        throwIfProjectIsNull(project);

        if (!project.getStudentResponsible().getId().equals(deliver.getStudentResponsible().getId())) {
            throw new PostSolutionFailedException();
        }

        deliver.setStudents(project.getStudents());
        deliver.setStudentResponsible(project.getStudentResponsible());
        deliver.getProjects().add(project);

        return projectService.setSolution(projectId, deliver);
    }

    public Student login(Map<String, String> login) {
        String password = login.get("password");
        String email = login.get("email");

        password =  Base64.getEncoder().encodeToString(password.getBytes());
        Student student = repository.findByEmailAndPassword(email, password);

        throwIfStudentIsNull(student);
        throwIfStudentIsInactive(student);

        return student;
    }

    public void activate(String b64) {
        JSONObject jsonObject = new JSONObject(new String(Base64.getDecoder().decode(b64)));
        Student found = repository.findByEmail(jsonObject.get("email").toString());
        throwIfStudentIsNull(found);

        found.setActive(true);
        repository.save(found);
    }

}
