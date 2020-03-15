package br.gov.sp.fatec.teacher.service;

import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.exception.TeacherException.TeacherNotFoundException;
import br.gov.sp.fatec.teacher.repository.TeacherRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static br.gov.sp.fatec.teacher.fixture.TeacherFixture.newTeacher;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTest {

    @InjectMocks
    private TeacherService service;

    @Mock
    private TeacherRepository repository;

    @Test
    public void save_shoudSucceed() {
        Teacher teacher = newTeacher();
        when(repository.save(teacher)).thenReturn(teacher);

        Teacher saved = service.save(teacher);

        assertEquals(teacher.getId(), saved.getId());
    }

    @Test
    public void deactivate_shouldSucceed() throws NotFoundException {
        Teacher teacher = newTeacher();
        when(repository.save(teacher)).thenReturn(teacher);
        when(repository.getOne(teacher.getId())).thenReturn(teacher);

        service.deactivate(teacher.getId());

        assertFalse(teacher.getActive());
    }

    @Test(expected = TeacherNotFoundException.class)
    public void deactivate_shouldFail() {
        service.deactivate(1L);
    }

    @Test
    public void findAll_shouldSucceed() {
        List<Teacher> teacherList = Lists.newArrayList(newTeacher(1L, true),
                newTeacher(2L, true),
                newTeacher(3L, true),
                newTeacher(4L, true),
                newTeacher(5L, true));

        when(repository.findAll()).thenReturn(teacherList);

        List<Teacher> found = service.findAll();

        assertEquals(teacherList.size(), found.size());
    }

    @Test
    public void findActive_shouldSucceed() {
        List<Teacher> teacherList = Lists.newArrayList(newTeacher(1L, true),
                newTeacher(2L, true),
                newTeacher(3L, true),
                newTeacher(4L, true),
                newTeacher(5L, true));

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
}
