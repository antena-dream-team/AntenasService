package br.gov.sp.fatec.project.controller;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import org.assertj.core.util.Lists;
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

import java.util.List;
import java.util.Objects;

import static br.gov.sp.fatec.project.fixture.ProjectFixture.newProject;
import static br.gov.sp.fatec.utils.commons.JSONParser.toJSON;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {

    private static final String URL = "/dev/project";

    private MockMvc mockMvc;

    @InjectMocks
    private ProjectController controller;

    @Mock
    private ProjectService service;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }
// todo - corrigir
//    @Test
//    public void create_shouldSucceed() throws Exception {
//        Project project = newProject();
//        when(service.save(project)).thenReturn(project);
//
//        mockMvc.perform(post(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Objects.requireNonNull(toJSON(project))))
//                .andExpect(status().isCreated());
//
//        verify(service).save(project);
//    }

    @Test
    public void findAll_shouldSucceed() throws Exception {
        List<Project> projectList = Lists.newArrayList(
                newProject(1L),
                newProject(2L),
                newProject(3L));

        when(service.findAll()).thenReturn(projectList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk());

        verify(service).findAll();
    }

    @Test
    public void findById_shouldSucceed() throws Exception {
        Project project = newProject();
        when(service.findById(project.getId())).thenReturn(project);

        mockMvc.perform(get(URL + "/" + project.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_shouldSucceed() throws Exception {
        Project project = newProject();

        mockMvc.perform(delete(URL + "/" + project.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void addStudent_shouldSucceed() throws Exception {
        Project project = newProject();

        when(service.addStudent(project.getId(), 1L)).thenReturn(project);
        mockMvc.perform(post(URL + "/add-student/" + project.getId() + "/" + 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void approve_shouldSucceed() throws Exception {
        Project project = newProject();
        when(service.approve(project.getId())).thenReturn(project);

        mockMvc.perform(put(URL + "/approve/" + project.getId()))
                .andExpect(status().isOk());
    }
}
