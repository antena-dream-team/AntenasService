package br.gov.sp.fatec.cadi.service;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.exception.CadiException.*;
import br.gov.sp.fatec.cadi.repository.CadiRepository;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.domain.Status;
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

import static br.gov.sp.fatec.cadi.fixture.CadiFixture.newCadi;
import static br.gov.sp.fatec.project.fixture.ProjectFixture.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CadiServiceTest {

    @InjectMocks
    private CadiService service;

    @Mock
    private CadiRepository repository;

    @Mock
    private ProjectService projectService;

    @Mock
    private SendEmail sendEmail;

    @Test
    public void save_shoudSucceed() {
        Cadi cadi = newCadi();
        when(repository.save(cadi)).thenReturn(cadi);

        Cadi saved = service.save(cadi);

        assertEquals(cadi.getId(), saved.getId());
    }

    @Test
    public void deactivate_shouldSucceed() {
        Cadi cadi = newCadi();
        when(repository.save(cadi)).thenReturn(cadi);
        when(repository.getOne(cadi.getId())).thenReturn(cadi);

        service.deactivate(cadi.getId());

        assertFalse(cadi.isActive());
    }

    @Test(expected = CadiNotFoundException.class)
    public void deactivate_shouldFail() {
        service.deactivate(1L);
    }

    @Test
    public void findAll_shouldSucceed() {
        List<Cadi> cadiList = Lists.newArrayList(newCadi(1L, true),
                newCadi(2L, true),
                newCadi(3L, true),
                newCadi(4L, true),
                newCadi(5L, true));

        when(repository.findAll()).thenReturn(cadiList);

        List<Cadi> found = service.findAll();

        assertEquals(cadiList.size(), found.size());
    }

    @Test
    public void findActive_shouldSucceed() {
        List<Cadi> cadiList = Lists.newArrayList(newCadi(1L, true),
                newCadi(2L, true),
                newCadi(3L, true),
                newCadi(4L, true),
                newCadi(5L, true));

        when(repository.findAllByActive(true)).thenReturn(cadiList);

        List<Cadi> found = service.findActive();

        assertEquals(cadiList.size(), found.size());
    }

    @Test
    public void findById_shouldSucceed() {
        Cadi cadi = newCadi();
        when(repository.findById(cadi.getId())).thenReturn(java.util.Optional.of(cadi));

        Cadi found = service.findById(cadi.getId());

        assertEquals(cadi.getId(), found.getId());
    }

    @Test(expected = CadiNotFoundException.class)
    public void findById_shouldFail() {
        service.findById(1L);
    }

    @Test
    public void update_shouldSucceed() {
        Cadi cadi = newCadi();
        Cadi updated = newCadi();
        updated.setEmail("newEmail@test.com");

        when(repository.findById(cadi.getId())).thenReturn(java.util.Optional.of(cadi));
        when(repository.save(updated)).thenReturn(updated);

        Cadi returned = service.update(cadi.getId(), updated);

        assertEquals(updated.getEmail(), returned.getEmail());
    }

    @Test(expected = CadiNotFoundException.class)
    public void update_shouldFail() {
        Cadi updated = newCadi();
        updated.setEmail("newEmail@test.com");

        service.update(2L, updated);
    }

    @Test
    public void activate_shouldSucceed() throws JSONException {
        Cadi cadi = newCadi();
        JSONObject base64 = new JSONObject();
        base64.put("dateTime", new Date());
        base64.put("email", cadi.getEmail());
        String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());

        when(repository.findByEmail((String) base64.get("email"))).thenReturn(cadi);
        when(repository.save(cadi)).thenReturn(cadi);
        service.activate(b64);
    }

    @Test(expected = CadiNotFoundException.class)
    public void activate_shouldFail() throws JSONException {
        Cadi cadi = newCadi();
        JSONObject base64 = new JSONObject();
        base64.put("dateTime", new Date());
        base64.put("email", cadi.getEmail());
        String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());

        service.activate(b64);
    }

    @Test
    public void setTeacher_shouldSucceed() {
        Project project = newProject();
        when(projectService.setTeacher(1L, project.getId())).thenReturn(project);
        service.setTeacher(1L, project.getId());
    }

    @Test
    public void setProjectStatus_shouldSucceed() {
        Project project = newProject();
        Status status = newStatus();

        when(projectService.setStatus(project.getId(), status)).thenReturn(project);
        service.setProjectStatus(project.getId(), status);
    }

    @Test
    public void setMeetingPossibleDate() {
        Project project = newProject();
        List<br.gov.sp.fatec.project.domain.Date> possibleDate = getPossibleDate();

        when(projectService.setMeetingPossibleDate(possibleDate, project.getId())).thenReturn(project);
        service.setMeetingPossibleDate(possibleDate, project.getId());
    }

    @Test
    public void login_shouldSucceed() {
        Cadi cadi = newCadi();

        Map<String, String> login = new HashMap<>();
        login.put("email", cadi.getEmail());
        login.put("password", cadi.getPassword());

        when(repository.findByEmailAndPassword(cadi.getEmail(), Base64.getEncoder().encodeToString(cadi.getPassword().getBytes())))
                .thenReturn(cadi);
        service.login(login);
    }

    @Test(expected = CadiNotFoundException.class)
    public void login_shouldFail_notFound() {
        Cadi cadi = newCadi();

        Map<String, String> login = new HashMap<>();
        login.put("email", cadi.getEmail());
        login.put("password", cadi.getPassword());

        service.login(login);
    }

    @Test(expected = CadiInactiveException.class)
    public void login_shouldFail_Inactive() {
        Cadi cadi = newCadi();
        cadi.setActive(false);

        Map<String, String> login = new HashMap<>();
        login.put("email", cadi.getEmail());
        login.put("password", cadi.getPassword());

        when(repository.findByEmailAndPassword(cadi.getEmail(), Base64.getEncoder().encodeToString(cadi.getPassword().getBytes())))
                .thenReturn(cadi);
        service.login(login);
    }
}