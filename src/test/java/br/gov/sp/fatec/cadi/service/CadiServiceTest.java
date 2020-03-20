package br.gov.sp.fatec.cadi.service;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.exception.CadiException;
import br.gov.sp.fatec.cadi.repository.CadiRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static br.gov.sp.fatec.cadi.fixture.CadiFixture.newCadi;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CadiServiceTest {

    @InjectMocks
    private CadiService service;

    @Mock
    private CadiRepository repository;

    @Test
    public void save_shoudSucceed() {
        Cadi cadi = newCadi();
        when(repository.save(cadi)).thenReturn(cadi);

        Cadi saved = service.save(cadi);

        assertEquals(cadi.getId(), saved.getId());
    }

    @Test
    public void deactivate_shouldSucceed() throws NotFoundException {
        Cadi cadi = newCadi();
        when(repository.save(cadi)).thenReturn(cadi);
        when(repository.getOne(cadi.getId())).thenReturn(cadi);

        service.deactivate(cadi.getId());

        assertFalse(cadi.isActive());
    }

    @Test(expected = CadiException.CadiNotFoundException.class)
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

    @Test(expected = CadiException.CadiNotFoundException.class)
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

    @Test(expected = CadiException.CadiNotFoundException.class)
    public void update_shouldFail() {
        Cadi updated = newCadi();
        updated.setEmail("newEmail@test.com");

        when(repository.getOne(2L)).thenReturn(null);

        service.update(2L, updated);
    }
}
