package br.gov.sp.fatec.student.service;

import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.exception.StudentException;
import br.gov.sp.fatec.student.repository.StudentRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static br.gov.sp.fatec.student.fixture.StudentFixture.newStudent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

    @InjectMocks
    private StudentService service;

    @Mock
    private StudentRepository repository;

//    @Test
//    public void save_shoudSucceed() {
//        Student student = newStudent();
//        when(repository.save(student)).thenReturn(student);
//
//        Student saved = service.save(student);
//
//        assertEquals(student.getId(), saved.getId());
//    }
//
//    @Test
//    public void deactivate_shouldSucceed() throws NotFoundException {
//        Student student = newStudent();
//        when(repository.save(student)).thenReturn(student);
//        when(repository.getOne(student.getId())).thenReturn(student);
//
//        service.deactivate(student.getId());
//
//        assertFalse(student.isActive());
//    }
//
////    @Test(expected = StudentException.StudentNotFoundException.class)
////    public void deactivate_shouldFail() {
////        service.deactivate(1L);
////    }
//
//    @Test
//    public void findAll_shouldSucceed() {
//        List<Student> studentList = Lists.newArrayList(newStudent(1L, true),
//                newStudent(2L, true),
//                newStudent(3L, true),
//                newStudent(4L, true),
//                newStudent(5L, true));
//
//        when(repository.findAll()).thenReturn(studentList);
//
//        List<Student> found = service.findAll();
//
//        assertEquals(studentList.size(), found.size());
//    }
//
//    @Test
//    public void findActive_shouldSucceed() {
//        List<Student> studentList = Lists.newArrayList(newStudent(1L, true),
//                newStudent(2L, true),
//                newStudent(3L, true),
//                newStudent(4L, true),
//                newStudent(5L, true));
//
//        when(repository.findAllByActive(true)).thenReturn(studentList);
//
//        List<Student> found = service.findActive();
//
//        assertEquals(studentList.size(), found.size());
//    }
//
//    @Test
//    public void findById_shouldSucceed() {
//        Student student = newStudent();
//        when(repository.getOne(student.getId())).thenReturn(student);
//
//        Student found = service.findById(student.getId());
//
//        assertEquals(student.getId(), found.getId());
//    }
//
//    @Test(expected = StudentException.StudentNotFoundException.class)
//    public void findById_shouldFail() {
//        when(repository.getOne(1L)).thenReturn(null);
//
//        service.findById(1L);
//    }
//
//    @Test
//    public void update_shouldSucceed() throws NotFoundException {
//        Student student = newStudent();
//        Student updated = newStudent();
//        updated.setEmail("newEmail@test.com");
//
//        when(repository.getOne(student.getId())).thenReturn(student);
//        when(repository.save(updated)).thenReturn(updated);
//
//        Student returned = service.update(student.getId(), updated);
//
//        assertEquals(updated.getEmail(), returned.getEmail());
//    }

    @Test(expected = NullPointerException.class)
    public void update_shouldFail() throws NotFoundException {
        Student updated = newStudent();
        updated.setEmail("newEmail@test.com");

        when(repository.getOne(2L)).thenReturn(null);

        service.update(2L, updated);
    }
}
