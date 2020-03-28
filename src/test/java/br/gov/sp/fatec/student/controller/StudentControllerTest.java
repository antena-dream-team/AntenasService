package br.gov.sp.fatec.student.controller;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.exception.StudentException.StudentLoginFailed;
import br.gov.sp.fatec.student.service.StudentService;
import com.google.inject.internal.util.Lists;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static br.gov.sp.fatec.project.fixture.ProjectFixture.newProject;
import static br.gov.sp.fatec.student.fixture.StudentFixture.newStudent;
import static br.gov.sp.fatec.utils.commons.JSONParser.toJSON;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    private static final String URL = "/dev/student";

    @InjectMocks
    private  StudentController controller;

    @Mock
    private StudentService service;

    private MockMvc mockMvc;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void create_shouldSucceed() throws Exception {
        Student student = newStudent();
        when(service.save(student)).thenReturn(student);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(toJSON(student))))
                .andExpect(status().isCreated());

        verify(service).save(student);
    }

    @Test
    public void findAll_shouldSucceed() throws Exception {
        List<Student> studentList = Lists.newArrayList(newStudent(1L, true),
                newStudent(2L, true),
                newStudent(3L, true));

        when(service.findAll()).thenReturn(studentList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk());

        verify(service).findAll();
    }

    @Test
    public void findActive_shouldSucceed() throws Exception {
        List<Student> studentList = Lists.newArrayList(newStudent(1L, true),
                newStudent(2L, true),
                newStudent(3L, true),
                newStudent(4L, true),
                newStudent(5L, true));

        when(service.findActive()).thenReturn(studentList);

        mockMvc.perform(get(URL + "/active"))
                .andExpect(status().isOk());
    }

    @Test
    public void findById_shouldSucceed() throws Exception {
        Student student = newStudent();
        when(service.findById(student.getId())).thenReturn(student);

        mockMvc.perform(get(URL + "/" + student.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void update_ShouldSucceed() throws Exception {
        Student student = newStudent();
        Student updated = newStudent();
        updated.setEmail("newEmail@test.com");

        mockMvc.perform(put(URL + "/" + student.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(toJSON(student))))
                .andExpect(status().isOk());
    }

    @Test
    public void deactivate_shouldSucceed() throws Exception {
        Student student = newStudent();
        student.setActive(false);

        mockMvc.perform(delete(URL + "/" + student.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void listProjectByStudent_shouldSucceed() throws Exception {
        List<Student> studentList =  Lists.newArrayList(newStudent());
        List<Project> projectList = Lists.newArrayList(
                newProject(1L),
                newProject(2L),
                newProject(3L));

        for(Project project : projectList) {
            project.setStudents(studentList);
        }

        when(service.findProjectByStudent(studentList.get(0).getId())).thenReturn(projectList);
        mockMvc.perform(get(URL + "/get-projects/" + studentList.get(0).getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void login_shouldSucceed() throws Exception {
        Student student = newStudent();

        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("email", student.getEmail());
        loginMap.put("password", student.getPassword());

        when(service.login(loginMap)).thenReturn(student);

        JSONObject loginObject = new JSONObject();
        loginObject.put("email", student.getEmail());
        loginObject.put("password", student.getPassword());

        mockMvc.perform(post(URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(loginObject)))
                .andExpect(status().isOk());
    }

    @Test
    public void deliverSolution_shouldSucceed() throws Exception {
        Project project = newProject();

        JSONObject deliverJson = new JSONObject();
        deliverJson.put("projectId",project.getId().toString());
        deliverJson.put("link", "github.com");

        Map<String, String> deliverMap = new HashMap<>();
        deliverMap.put("projectId", project.getId().toString());
        deliverMap.put("link", "github.com");

        when(service.setSolution(deliverMap)).thenReturn(project);
        mockMvc.perform(post(URL + "/deliver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(deliverJson)))
                .andExpect(status().isOk());
    }
}
