package br.gov.sp.fatec.student.service;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.exception.StudentException;
import br.gov.sp.fatec.student.exception.StudentException.*;
import br.gov.sp.fatec.student.repository.StudentRepository;
import br.gov.sp.fatec.utils.commons.SendEmail;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.assertj.core.util.Lists;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static br.gov.sp.fatec.project.fixture.ProjectFixture.newProject;
import static br.gov.sp.fatec.student.fixture.StudentFixture.newStudent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService service;

    @Mock
    private StudentRepository repository;

    @Mock
    private ProjectService projectService;

    @Mock
    private SendEmail sendEmail;

    @Test
    public void save_shoudSucceed() {
        Student student = newStudent();
        when(repository.save(student)).thenReturn(student);

        Student saved = service.save(student);

        assertEquals(student.getId(), saved.getId());
    }

    @Test
    public void deactivate_shouldSucceed() throws NotFoundException {
        Student student = newStudent();
        when(repository.save(student)).thenReturn(student);
        when(repository.findById(student.getId())).thenReturn(java.util.Optional.of(student));

        service.deactivate(student.getId());

        assertFalse(student.isActive());
    }

    @Test(expected = StudentNotFoundException.class)
    public void deactivate_shouldFail() throws NotFoundException {
        service.deactivate(1L);
    }

    @Test
    public void findAll_shouldSucceed() {
        List<Student> studentList = Lists.newArrayList(
                newStudent(1L, true),
                newStudent(2L, true),
                newStudent(3L, true));

        when(repository.findAll()).thenReturn(studentList);

        List<Student> found = service.findAll();

        assertEquals(studentList.size(), found.size());
    }

    @Test
    public void findActive_shouldSucceed() {
        List<Student> studentList = Lists.newArrayList(
                newStudent(1L, true),
                newStudent(2L, true),
                newStudent(3L, true));

        when(repository.findAllByActive(true)).thenReturn(studentList);

        List<Student> found = service.findActive();

        assertEquals(studentList.size(), found.size());
    }

    @Test
    public void findById_shouldSucceed() {
        Student student = newStudent();
        when(repository.getOne(student.getId())).thenReturn(student);
        Student found = service.findById(student.getId());
        assertEquals(student.getId(), found.getId());
    }

    @Test(expected = StudentNotFoundException.class)
    public void findById_shouldFail() {
        when(repository.getOne(1L)).thenReturn(null);
        service.findById(1L);
    }

    @Test
    public void update_shouldSucceed() {
        Student student = newStudent();
        Student updated = newStudent();
        updated.setEmail("newEmail@test.com");

        when(repository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
        Student returned = service.update(student.getId(), updated);
        assertEquals(updated.getEmail(), returned.getEmail());
    }

    @Test(expected = StudentNotFoundException.class)
    public void update_shouldFail() {
        Student updated = newStudent();
        updated.setEmail("newEmail@test.com");
        service.update(2L, updated);
    }

    @Test
    public void findProjectByStudent_shouldSucceed() {
        List<Student> studentList = Lists.newArrayList(newStudent());
        List<Project> projectList = Lists.newArrayList(
                newProject(1L),
                newProject(2L),
                newProject(3L));

        for(Project project : projectList) {
            project.setStudents(studentList);
        }

        when(projectService.getProjectByStudent(studentList.get(0).getId())).thenReturn(projectList);
        service.findProjectByStudent(studentList.get(0).getId());
    }

    @Test
    public void setSolution_shouldSucceed() {
        Project project = newProject();
        String link = "github.com";

        Map<String, String> deliver = new HashMap<>();
        deliver.put("projectId", project.getId().toString());
        deliver.put("link", link);

        when(projectService.setSolution(project.getId(), link)).thenReturn(project);
        service.setSolution(deliver);
    }

    @Test
    public void login_shouldSucceed() {
        Student student = newStudent();

        Map<String, String> login = new HashMap<>();
        login.put("email", student.getEmail());
        login.put("password", student.getPassword());

        when(repository.findByEmailAndPassword(student.getEmail(), Base64.getEncoder().encodeToString(student.getPassword().getBytes())))
                .thenReturn(student);
        service.login(login);
    }

    @Test(expected = StudentNotFoundException.class)
    public void login_shouldFail_notFound() {
        Student student = newStudent();

        Map<String, String> login = new HashMap<>();
        login.put("email", student.getEmail());
        login.put("password", student.getPassword());

        service.login(login);
    }

    @Test(expected = StudentInactiveException.class)
    public void login_shouldFail_Inactive() {
        Student student = newStudent();
        student.setActive(false);

        Map<String, String> login = new HashMap<>();
        login.put("email", student.getEmail());
        login.put("password", student.getPassword());

        when(repository.findByEmailAndPassword(student.getEmail(), Base64.getEncoder().encodeToString(student.getPassword().getBytes())))
                .thenReturn(student);
        service.login(login);
    }

    @Test
    public void findAllById_shouldSucceed() {
        List<Student> studentList = Lists.newArrayList(
                newStudent(1L, true),
                newStudent(2L, true),
                newStudent(3L, true));

        List<Long> idList = Lists.newArrayList(1L, 2L, 3L);
        when(repository.findAllById(idList)).thenReturn(studentList);

        service.findAllById(idList);
    }


    @Test
    public void activate_shouldSucceed() throws JSONException {
        Student student = newStudent();
        JSONObject base64 = new JSONObject();
        base64.put("dateTime", new Date());
        base64.put("email", student.getEmail());
        String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());

        when(repository.findByEmail((String) base64.get("email"))).thenReturn(student);
        service.activate(b64);
    }

    @Test(expected = StudentException.StudentNotFoundException.class)
    public void activate_shouldFail() throws JSONException {
        // todo - verificar se ativou
        Student student = newStudent();
        JSONObject base64 = new JSONObject();
        base64.put("dateTime", new Date());
        base64.put("email", student.getEmail());
        String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());

        service.activate(b64);
    }
}
