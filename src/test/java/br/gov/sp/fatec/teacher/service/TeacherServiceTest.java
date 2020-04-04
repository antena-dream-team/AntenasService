package br.gov.sp.fatec.teacher.service;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.exception.TeacherException.*;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import br.gov.sp.fatec.utils.commons.SendEmail;
import org.assertj.core.util.Lists;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import static br.gov.sp.fatec.project.fixture.ProjectFixture.newProject;
import static br.gov.sp.fatec.student.fixture.StudentFixture.newStudent;
import static br.gov.sp.fatec.teacher.fixture.TeacherFixture.newTeacher;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTest {

    @InjectMocks
    private TeacherService service;

    @Mock
    private TeacherRepository repository;

    @Mock
    private ProjectService projectService;

    @Mock
    private SendEmail sendEmail;

    @Test
    public void save_shoudSucceed() {
        Teacher teacher = newTeacher();
        when(repository.save(teacher)).thenReturn(teacher);

        Teacher saved = service.save(teacher);

        assertEquals(teacher.getId(), saved.getId());
    }

    @Test
    public void findAll_shouldSucceed() {
        List<Teacher> teacherList = Lists.newArrayList(
                newTeacher(1L, true),
                newTeacher(2L, true),
                newTeacher(3L, true));

        when(repository.findAll()).thenReturn(teacherList);

        List<Teacher> found = service.findAll();

        assertEquals(teacherList.size(), found.size());
    }

    @Test
    public void findActive_shouldSucceed() {
        List<Teacher> teacherList = Lists.newArrayList(
                newTeacher(1L, true),
                newTeacher(2L, true),
                newTeacher(3L, true));

        when(repository.findAllByActive(true)).thenReturn(teacherList);

        List<Teacher> found = service.findActive();

        assertEquals(teacherList.size(), found.size());
    }

    @Test
    public void findById_shouldSucceed() {
        Teacher teacher = newTeacher();
        when(repository.getOne(teacher.getId())).thenReturn(teacher);

        Teacher found = service.findById(teacher.getId());

        assertEquals(teacher.getId(), found.getId());
    }

    @Test(expected = TeacherNotFoundException.class)
    public void findById_shouldFail() {
        when(repository.getOne(1L)).thenReturn(null);

        service.findById(1L);
    }

    @Test
    public void update_shouldSucceed() {
        Teacher teacher = newTeacher();
        Teacher updated = newTeacher();
        updated.setEmail("newEmail@test.com");

        when(repository.getOne(teacher.getId())).thenReturn(teacher);
        when(repository.save(updated)).thenReturn(updated);

        Teacher returned = service.update(teacher.getId(), updated);

        assertEquals(updated.getEmail(), returned.getEmail());
    }

    @Test(expected = TeacherNotFoundException.class)
    public void update_shouldFail() {
        Teacher updated = newTeacher();
        updated.setEmail("newEmail@test.com");

        when(repository.getOne(2L)).thenReturn(null);

        service.update(2L, updated);
    }

    @Test
    public void setStudentsToProject_shouldSucceed() {
        Project project = newProject();
        Teacher teacher = newTeacher();

        List<Student> studentList = Lists.newArrayList(newStudent(1L, true),
                newStudent(2L, true),
                newStudent(3L, true));

        project.setStudents(studentList);

        when(repository.getOne(teacher.getId())).thenReturn(teacher);
        when(projectService.findById(project.getId())).thenReturn(project);
        when(projectService.setStudents(project.getId(), studentList)).thenReturn(project);

        service.setStudentsToProject(studentList, project.getId(), teacher.getId());
        assertEquals(studentList.size(), project.getStudents().size());
    }

    @Test(expected = CannotAddOrRemoveStudentsToThisProject.class)
    public void setStudentsToProject_shouldFail() {
        Project project = newProject();
        Teacher teacher = newTeacher();
        teacher.setId(2L);

        List<Student> studentList = Lists.newArrayList(newStudent(1L, true),
                newStudent(2L, true),
                newStudent(3L, true));

        project.setStudents(studentList);

        when(repository.getOne(teacher.getId())).thenReturn(teacher);
        when(projectService.findById(project.getId())).thenReturn(project);
        when(projectService.setStudents(project.getId(), studentList)).thenReturn(project);

        service.setStudentsToProject(studentList, project.getId(), teacher.getId());
        assertEquals(studentList.size(), project.getStudents().size());
    }

    @Test
    public void setStudentsResponsibleToProject_shouldSucceed() {
        Project project = newProject();
        Student student = newStudent();
        Teacher teacher = newTeacher();
        project.setStudentResponsible(student);

        when(repository.getOne(teacher.getId())).thenReturn(teacher);
        when(projectService.findById(project.getId())).thenReturn(project);
        when(projectService.setStudentResponsible(project.getId(), student.getId())).thenReturn(project);
        Project returned = service.setStudentsResponsibleToProject(student.getId(), project.getId(), teacher.getId());
        assertEquals(returned, project);
    }

    @Test(expected = CannotAddOrRemoveStudentsToThisProject.class)
    public void setStudentsResponsibleToProject_shouldFail() {
        Project project = newProject();
        Student student = newStudent();
        Teacher teacher = newTeacher();
        teacher.setId(2L);
        project.setStudentResponsible(student);

        when(repository.getOne(teacher.getId())).thenReturn(teacher);
        when(projectService.findById(project.getId())).thenReturn(project);
        when(projectService.setStudentResponsible(project.getId(), student.getId())).thenReturn(project);
        Project returned = service.setStudentsResponsibleToProject(student.getId(), project.getId(), teacher.getId());
        assertEquals(returned, project);
    }

    @Test
    public void listProjectByTeacher_shouldSucceed() {
        Teacher teacher = newTeacher();
        List<Project> projectList = Lists.newArrayList(
                newProject(1L),
                newProject(2L),
                newProject(3L));

        for(Project project : projectList) {
            project.setTeacher(teacher);
        }

        when(projectService.getProjectByTeacher(teacher.getId())).thenReturn(projectList);
        List<Project> returned = service.listProjectByTeacher(teacher.getId());
        assertEquals(projectList, returned);
    }

    @Test
    public void removeStudent_shouldSucced() {
        Project project = newProject();
        when(projectService.removeStudents(project.getId(), 1L)).thenReturn(project);
        service.removeStudent(project.getId(), 1L);
    }

    @Test
    public void login_shouldSucceed() {
        Teacher teacher = newTeacher();
        when(repository.findByEmailAndPassword(teacher.getEmail(), Base64.getEncoder().encodeToString(teacher.getPassword().getBytes()))).thenReturn(teacher);
        service.login(teacher.getEmail(), teacher.getPassword());
    }

    @Test(expected = TeacherNotFoundException.class)
    public void login_shouldFail_notFound() {
        Teacher teacher = newTeacher();
        service.login(teacher.getEmail(), teacher.getPassword());
    }

    @Test(expected = TeacherInactiveException.class)
    public void login_shouldFail_Inactive() {
        Teacher teacher = newTeacher();
        teacher.setActive(false);

        when(repository.findByEmailAndPassword(teacher.getEmail(), Base64.getEncoder().encodeToString(teacher.getPassword().getBytes()))).thenReturn(teacher);
        service.login(teacher.getEmail(), teacher.getPassword());
    }

    @Test
    public void activate_shouldSucceed() throws JSONException {
        Teacher teacher = newTeacher();
        JSONObject base64 = new JSONObject();
        base64.put("dateTime", new Date());
        base64.put("email", teacher.getEmail());
        String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());

        when(repository.findByEmail((String) base64.get("email"))).thenReturn(teacher);
        service.activate(b64);
        assertTrue(teacher.getActive());
    }

    @Test(expected = TeacherNotFoundException.class)
    public void activate_shouldFail() throws JSONException {
        Teacher teacher = newTeacher();
        JSONObject base64 = new JSONObject();
        base64.put("dateTime", new Date());
        base64.put("email", teacher.getEmail());
        String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());

        service.activate(b64);
    }
}
