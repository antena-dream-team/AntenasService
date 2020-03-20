package br.gov.sp.fatec.entrepreneur.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.exception.EntrepreneurException;
import br.gov.sp.fatec.entrepreneur.repository.EntrepreneurRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static br.gov.sp.fatec.entrepreneur.fixture.EntrepreneurFixture.newEntrepreneur;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EntrepreneurServiceTest {
    @InjectMocks
    private EntrepreneurService service;

    @Mock
    private EntrepreneurRepository repository;

    @Test
    public void save_shoudSucceed() {
        Entrepreneur entrepreneur = newEntrepreneur();
        when(repository.save(entrepreneur)).thenReturn(entrepreneur);

        Entrepreneur saved = service.save(entrepreneur);

        assertEquals(entrepreneur.getId(), saved.getId());
    }

    @Test
    public void deactivate_shouldSucceed() throws NotFoundException {
        Entrepreneur entrepreneur = newEntrepreneur();
        when(repository.save(entrepreneur)).thenReturn(entrepreneur);
        when(repository.getOne(entrepreneur.getId())).thenReturn(entrepreneur);

        service.deactivate(entrepreneur.getId());

        assertFalse(entrepreneur.isActive());
    }

    @Test(expected = EntrepreneurException.EntrepreneurNotFoundException.class)
    public void deactivate_shouldFail() {
        service.deactivate(1L);
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

    @Test(expected = EntrepreneurException.EntrepreneurNotFoundException.class)
    public void findById_shouldFail() {
        when(repository.getOne(1L)).thenReturn(null);

        service.findById(1L);
    }

    @Test
    public void update_shouldSucceed() {
        Entrepreneur entrepreneur = newEntrepreneur();
        Entrepreneur updated = newEntrepreneur();
        updated.setEmail("newEmail@test.com");

        when(repository.getOne(entrepreneur.getId())).thenReturn(entrepreneur);
        when(repository.save(updated)).thenReturn(updated);

        Entrepreneur returned = service.update(entrepreneur.getId(), updated);

        assertEquals(updated.getEmail(), returned.getEmail());
    }

    @Test(expected = EntrepreneurException.EntrepreneurNotFoundException.class)
    public void update_shouldFail() {
        Entrepreneur updated = newEntrepreneur();
        updated.setEmail("newEmail@test.com");

        when(repository.getOne(2L)).thenReturn(null);

        service.update(2L, updated);
    }
}
