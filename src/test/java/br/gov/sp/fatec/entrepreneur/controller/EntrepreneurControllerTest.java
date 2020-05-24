package br.gov.sp.fatec.entrepreneur.controller;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.service.EntrepreneurService;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
import org.assertj.core.util.Lists;
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

import java.util.*;

import static br.gov.sp.fatec.entrepreneur.fixture.EntrepreneurFixture.newEntrepreneur;
import static br.gov.sp.fatec.project.fixture.ProjectFixture.newProject;
import static br.gov.sp.fatec.utils.commons.JSONParser.toJSON;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EntrepreneurControllerTest {
    private static final String URL = "/dev/entrepreneur";

    @InjectMocks
    private EntrepreneurController controller;

    @Mock
    private EntrepreneurService service;

    @Mock
    private ProjectService projectService;

    private MockMvc mockMvc;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void create_shouldSucceed() throws Exception {
        Entrepreneur entrepreneur = newEntrepreneur();
        when(service.save(entrepreneur)).thenReturn(entrepreneur);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(toJSON(entrepreneur))))
                .andExpect(status().isCreated());

        verify(service).save(entrepreneur);
    }

    @Test
    public void findAll_shouldSucceed() throws Exception {
        List<Entrepreneur> entrepreneurList = Lists.newArrayList(newEntrepreneur(1L, true),
                newEntrepreneur(2L, true),
                newEntrepreneur(3L, true));

        when(service.findAll()).thenReturn(entrepreneurList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk());

        verify(service).findAll();
    }

    @Test
    public void findActive_shouldSucceed() throws Exception {
        List<Entrepreneur> entrepreneurList = Lists.newArrayList(newEntrepreneur(1L, true),
                newEntrepreneur(2L, true),
                newEntrepreneur(3L, true),
                newEntrepreneur(4L, true),
                newEntrepreneur(5L, true));

        when(service.findActive()).thenReturn(entrepreneurList);

        mockMvc.perform(get(URL + "/active"))
                .andExpect(status().isOk());
    }

    @Test
    public void findById_shouldSucceed() throws Exception {
        Entrepreneur entrepreneur = newEntrepreneur();
        when(service.findById(entrepreneur.getId())).thenReturn(entrepreneur);

        mockMvc.perform(get(URL + "/" + entrepreneur.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void update_ShouldSucceed() throws Exception {
        Entrepreneur entrepreneur = newEntrepreneur();
        Entrepreneur updated = newEntrepreneur();
        updated.setEmail("newEmail@test.com");

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(toJSON(entrepreneur))))
                .andExpect(status().isOk());
    }

    @Test
    public void login_shouldSucceed() throws Exception {
        Entrepreneur entrepreneur = newEntrepreneur();

        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("email", entrepreneur.getEmail());
        loginMap.put("password", entrepreneur.getPassword());

        when(service.login(loginMap)).thenReturn(entrepreneur);

        JSONObject loginObject = new JSONObject();
        loginObject.put("email", entrepreneur.getEmail());
        loginObject.put("password", entrepreneur.getPassword());

        mockMvc.perform(post(URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(loginObject)))
                .andExpect(status().isOk());
    }

    @Test
    public void activate_shouldSucceed() throws Exception {
        Entrepreneur entrepreneur = newEntrepreneur();
        JSONObject base64 = new JSONObject();
        base64.put("dateTime", new Date());
        base64.put("email", entrepreneur.getEmail());
        String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());

        mockMvc.perform(get(URL + "/activate/" + b64))
                .andExpect(status().isOk());
    }

    @Test
    public void update_shouldSucceed() throws Exception {
        Project project = newProject();

        mockMvc.perform(put(URL + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(toJSON(project))))
                .andExpect(status().isOk());
    }
}
