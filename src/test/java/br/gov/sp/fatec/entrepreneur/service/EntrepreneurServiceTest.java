package br.gov.sp.fatec.entrepreneur.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.exception.EntrepreneurException.*;
import br.gov.sp.fatec.entrepreneur.repository.EntrepreneurRepository;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.service.ProjectService;
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

import static br.gov.sp.fatec.entrepreneur.fixture.EntrepreneurFixture.newEntrepreneur;
import static br.gov.sp.fatec.project.fixture.ProjectFixture.newProject;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EntrepreneurServiceTest {
    @InjectMocks
    private EntrepreneurService service;

    @Mock
    private EntrepreneurRepository repository;

    @Mock
    private ProjectService projectService;

    @Mock
    private SendEmail sendEmail;

    @Test
    public void save_shoudSucceed() {
        Entrepreneur entrepreneur = newEntrepreneur();
        when(repository.save(entrepreneur)).thenReturn(entrepreneur);

        Entrepreneur saved = service.save(entrepreneur);

        assertEquals(entrepreneur.getId(), saved.getId());
    }

    @Test
    public void findAll_shouldSucceed() {
        List<Entrepreneur> entrepreneurList = Lists.newArrayList(newEntrepreneur(1L, true),
                newEntrepreneur(2L, true),
                newEntrepreneur(3L, true),
                newEntrepreneur(4L, true),
                newEntrepreneur(5L, true));

        when(repository.findAll()).thenReturn(entrepreneurList);

        List<Entrepreneur> found = service.findAll();

        assertEquals(entrepreneurList.size(), found.size());
    }

    @Test
    public void findActive_shouldSucceed() {
        List<Entrepreneur> entrepreneurList = Lists.newArrayList(newEntrepreneur(1L, true),
                newEntrepreneur(2L, true),
                newEntrepreneur(3L, true),
                newEntrepreneur(4L, true),
                newEntrepreneur(5L, true));

        when(repository.findAllByActive(true)).thenReturn(entrepreneurList);

        List<Entrepreneur> found = service.findActive();

        assertEquals(entrepreneurList.size(), found.size());
    }

    @Test
    public void findById_shouldSucceed() {
        Entrepreneur entrepreneur = newEntrepreneur();
        when(repository.getOne(entrepreneur.getId())).thenReturn(entrepreneur);

        Entrepreneur found = service.findById(entrepreneur.getId());

        assertEquals(entrepreneur.getId(), found.getId());
    }

    @Test(expected = EntrepreneurNotFoundException.class)
    public void findById_shouldFail() {
        service.findById(1L);
    }

    @Test
    public void update_shouldSucceed() {
        Entrepreneur entrepreneur = newEntrepreneur();
        Entrepreneur updated = newEntrepreneur();
        updated.setEmail("newEmail@test.com");

        when(repository.findById(entrepreneur.getId())).thenReturn(java.util.Optional.of(entrepreneur));
        when(repository.save(updated)).thenReturn(updated);

        Entrepreneur returned = service.update(entrepreneur.getId(), updated);

        assertEquals(updated.getEmail(), returned.getEmail());
    }

    @Test(expected = EntrepreneurNotFoundException.class)
    public void update_shouldFail() {
        Entrepreneur updated = newEntrepreneur();
        updated.setEmail("newEmail@test.com");

        service.update(2L, updated);
    }

    @Test
    public void login_shouldSucceed() {
        Entrepreneur entrepreneur = newEntrepreneur();

        Map<String, String> login = new HashMap<>();
        login.put("email", entrepreneur.getEmail());
        login.put("password", entrepreneur.getPassword());

        when(repository.findByEmailAndPassword(entrepreneur.getEmail(), Base64.getEncoder().encodeToString(entrepreneur.getPassword().getBytes()))).thenReturn(entrepreneur);
        service.login(login);
    }

    @Test(expected = EntrepreneurNotFoundException.class)
    public void login_shouldFail_notFound() {
        Entrepreneur entrepreneur = newEntrepreneur();

        Map<String, String> login = new HashMap<>();
        login.put("email", entrepreneur.getEmail());
        login.put("password", entrepreneur.getPassword());

        service.login(login);
    }

    @Test(expected = EntrepreneurInactiveException.class)
    public void login_shouldFail_Inactive() {
        Entrepreneur entrepreneur = newEntrepreneur();
        entrepreneur.setActive(false);

        Map<String, String> login = new HashMap<>();
        login.put("email", entrepreneur.getEmail());
        login.put("password", entrepreneur.getPassword());

        when(repository.findByEmailAndPassword(entrepreneur.getEmail(), Base64.getEncoder().encodeToString(entrepreneur.getPassword().getBytes()))).thenReturn(entrepreneur);
        service.login(login);
    }

    @Test
    public void getProjectByEntrepreneur_shouldSucceed() {
        Entrepreneur entrepreneur = newEntrepreneur();
        List<Project> projectList = Lists.newArrayList(
                newProject(1L),
                newProject(2L),
                newProject(3L));

        for(Project project : projectList) {
            project.setEntrepreneur(entrepreneur);
        }

        when(projectService.getProjectByEntrepreneur(entrepreneur.getId())).thenReturn(projectList);
        service.getProjectByEntrepreneur(entrepreneur.getId());
    }

    @Test
    public void setMeetingChosenDate_shouldSucceed() {
        Project project = newProject();
        when (projectService.setMeetingChosenDate(1L, project.getId())).thenReturn(project);
        service.setMeetingChosenDate(1L, project.getId());
    }

    @Test
    public void activate_shouldSucceed() throws JSONException {
        Entrepreneur entrepreneur = newEntrepreneur();
        JSONObject base64 = new JSONObject();
        base64.put("dateTime", new Date());
        base64.put("email", entrepreneur.getEmail());
        String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());

        when(repository.findByEmail((String) base64.get("email"))).thenReturn(entrepreneur);
        service.activate(b64);
        assertTrue(entrepreneur.isActive());
    }

    @Test(expected = EntrepreneurNotFoundException.class)
    public void activate_shouldFail() throws JSONException {
        Entrepreneur entrepreneur = newEntrepreneur();
        JSONObject base64 = new JSONObject();
        base64.put("dateTime", new Date());
        base64.put("email", entrepreneur.getEmail());
        String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());

        service.activate(b64);
    }
}
