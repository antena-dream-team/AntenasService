package br.gov.sp.fatec.project.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.exception.EntrepreneurException.EntrepreneurInactiveException;
import br.gov.sp.fatec.entrepreneur.exception.EntrepreneurException.EntrepreneurNotFoundException;
import br.gov.sp.fatec.entrepreneur.service.EntrepreneurService;
import br.gov.sp.fatec.project.domain.Date;
import br.gov.sp.fatec.project.domain.Deliver;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.domain.Status;
import br.gov.sp.fatec.project.exception.ProjectException.*;
import br.gov.sp.fatec.project.repository.ProjectRepository;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.exception.StudentException.StudentInactiveException;
import br.gov.sp.fatec.student.exception.StudentException.StudentNotFoundException;
import br.gov.sp.fatec.student.service.StudentService;
import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.exception.TeacherException.TeacherInactiveException;
import br.gov.sp.fatec.teacher.exception.TeacherException.TeacherNotFoundException;
import br.gov.sp.fatec.teacher.service.TeacherService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static br.gov.sp.fatec.project.fixture.ProjectFixture.newDeliver;
import static br.gov.sp.fatec.project.fixture.ProjectFixture.newProject;
import static br.gov.sp.fatec.student.fixture.StudentFixture.newStudent;
import static br.gov.sp.fatec.teacher.fixture.TeacherFixture.newTeacher;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService service;

    @Mock
    private ProjectRepository repository;

    @Mock
    private TeacherService teacherService;

    @Mock
    private EntrepreneurService entrepreneurService;

    @Mock
    private StudentService studentService;

    @Test
    public void save_shouldSucceed() {
        Project project = newProject();
        Teacher teacher = project.getTeacher();
        Entrepreneur entrepreneur = project.getEntrepreneur();
        Student studentResponsible = project.getStudentResponsible();

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        when(entrepreneurService.findById(entrepreneur.getId())).thenReturn(entrepreneur);
        when(studentService.findById(studentResponsible.getId())).thenReturn(studentResponsible);
        when(repository.save(project)).thenReturn(project);

        Project returned = service.save(project);
        assertNotNull(returned);
    }

    @Test(expected = TeacherNotFoundException.class)
    public void save_shouldFail_teacherNotFound() {
        Project project = newProject();
        service.save(project);
    }

    @Test(expected = TeacherInactiveException.class)
    public void save_shouldFail_teacherInactive() {
        Project project = newProject();
        Teacher teacher = project.getTeacher();
        teacher.setActive(false);

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        service.save(project);
    }

    @Test(expected = EntrepreneurNotFoundException.class)
    public void save_shouldFail_entrepreneurNotFound() {
        Project project = newProject();
        Teacher teacher = project.getTeacher();

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        service.save(project);
    }

    @Test(expected = EntrepreneurInactiveException.class)
    public void save_shouldFail_entrepreneurInactive() {
        Project project = newProject();
        Teacher teacher = project.getTeacher();
        Entrepreneur entrepreneur = project.getEntrepreneur();
        entrepreneur.setActive(false);

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        when(entrepreneurService.findById(entrepreneur.getId())).thenReturn(entrepreneur);
        service.save(project);
    }

    @Test(expected = StudentNotFoundException.class)
    public void save_shouldFail_studentResponsibleNotFound() {
        Project project = newProject();
        Teacher teacher = project.getTeacher();
        Entrepreneur entrepreneur = project.getEntrepreneur();

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        when(entrepreneurService.findById(entrepreneur.getId())).thenReturn(entrepreneur);

        service.save(project);
    }

    @Test(expected = StudentInactiveException.class)
    public void save_shouldFail_studentResponsibleInactive() {
        Project project = newProject();
        Teacher teacher = project.getTeacher();
        Entrepreneur entrepreneur = project.getEntrepreneur();
        Student studentResponsible = project.getStudentResponsible();
        studentResponsible.setActive(false);

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        when(entrepreneurService.findById(entrepreneur.getId())).thenReturn(entrepreneur);
        when(studentService.findById(studentResponsible.getId())).thenReturn(studentResponsible);

        service.save(project);
    }

    @Test(expected = StudentNotFoundException.class)
    public void save_shouldFail_studentNotFound() {
        Project project = newProject();
        project.getStudents().get(0).setId(5L);
        Teacher teacher = project.getTeacher();
        Entrepreneur entrepreneur = project.getEntrepreneur();

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        when(entrepreneurService.findById(entrepreneur.getId())).thenReturn(entrepreneur);
        when(studentService.findById(project.getStudents().get(0).getId())).thenReturn(null);

        service.save(project);
    }

    @Test(expected = StudentInactiveException.class)
    public void save_shouldFail_studentInactive() {
        Project project = newProject();
        project.getStudents().get(0).setActive(false);
        project.getStudents().get(0).setId(5L);
        Teacher teacher = project.getTeacher();
        Entrepreneur entrepreneur = project.getEntrepreneur();

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        when(entrepreneurService.findById(entrepreneur.getId())).thenReturn(entrepreneur);
        when(studentService.findById(project.getStudents().get(0).getId())).thenReturn(project.getStudents().get(0));

        service.save(project);
    }

    @Test
    public void findAll_shouldSucceed() {
        List<Project> projectList = Lists.newArrayList(
                newProject(1L),
                newProject(2L),
                newProject(3L));

        when(repository.findAll()).thenReturn(projectList);
        assertNotNull(service.findAll());
    }

    @Test
    public void findById_shouldSucceed() {
        Project project = newProject();
        when(repository.getOne(project.getId())).thenReturn(project);
        assertNotNull(service.findById(project.getId()));
    }

    @Test
    public void delete_shouldSucceed() {
        Project project = newProject();
        when(repository.getOne(project.getId())).thenReturn(project);
        service.delete(project.getId());
    }

    @Test(expected = ProjectNotFoundException.class)
    public void delete_shouldFail() {
        when(repository.getOne(1L)).thenReturn(null);
        service.delete(1L);
    }

    @Test
    public void setStudentResponsible_shouldSucceed() {
        Project project = newProject();
        Student student = newStudent();

        when(repository.getOne(project.getId())).thenReturn(project);
        when(studentService.findById(student.getId())).thenReturn(student);
        when(repository.save(project)).thenReturn(project);

        Project returned = service.setStudentResponsible(project.getId(), student.getId());
        assertNotNull(returned);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void setStudentResponsible_shouldFail_projectNotFound() {
        service.setStudentResponsible(1L, 1L);
    }

    @Test(expected = StudentNotFoundException.class)
    public void setStudentResponsible_shouldFail_studentIsNull() {
        Project project = newProject();
        Student student = newStudent();

        when(repository.getOne(project.getId())).thenReturn(project);
        service.setStudentResponsible(project.getId(), student.getId());
    }

    @Test(expected = StudentInactiveException.class)
    public void setStudentResponsible_shouldFail_studentInactive() {
        Project project = newProject();
        Student student = newStudent();
        student.setActive(false);

        when(repository.getOne(project.getId())).thenReturn(project);
        when(studentService.findById(student.getId())).thenReturn(student);

        service.setStudentResponsible(project.getId(), student.getId());
    }

    @Test
    public void setStudents_shouldSucceed() {
        Project project = newProject();
        List<Student> studentList = project.getStudents();

        when(studentService.findById(studentList.get(0).getId())).thenReturn(studentList.get(0));
        when(repository.save(project)).thenReturn(project);
        when(repository.getOne(project.getId())).thenReturn(project);

        Project returned = service.setStudents(project.getId(), studentList);
        assertNotNull(returned);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void setStudents_shouldFail_projectNotFound() {
        Project project = newProject();
        List<Student> studentList = project.getStudents();
        service.setStudents(project.getId(), studentList);
    }

    @Test(expected = StudentNotFoundException.class)
    public void setStudents_shouldFail_studentNotFound() {
        Project project = newProject();
        List<Student> studentList = project.getStudents();

        when(repository.getOne(project.getId())).thenReturn(project);

        service.setStudents(project.getId(), studentList);
    }

    @Test(expected = StudentInactiveException.class)
    public void setStudents_shouldFail_studentInactive() {
        Project project = newProject();
        List<Student> studentList = project.getStudents();

        studentList.get(0).setActive(false);

        when(studentService.findById(studentList.get(0).getId())).thenReturn(studentList.get(0));
        when(repository.getOne(project.getId())).thenReturn(project);

        Project returned = service.setStudents(project.getId(), studentList);
        assertNotNull(returned);
    }

    @Test
    public void setTeacher_shouldSucceed() {
        Project project = newProject();
        Teacher teacher = newTeacher();

        when(repository.getOne(project.getId())).thenReturn(project);
        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        when(repository.save(project)).thenReturn(project);

        Project returned = service.setTeacher(project.getId(), teacher.getId());
        assertNotNull(returned);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void setTeacher_shouldFail_projectNotFound() {
        service.setTeacher(1L, 1L);
    }

    @Test(expected = TeacherNotFoundException.class)
    public void setTeacher_shouldFail_teacherNotFound() {
        Project project = newProject();
        Teacher teacher = newTeacher();

        when(repository.getOne(project.getId())).thenReturn(project);

        Project returned = service.setTeacher(project.getId(), teacher.getId());
        assertNotNull(returned);
    }

    @Test(expected = TeacherInactiveException.class)
    public void setTeacher_shouldFail_teacherInactive() {
        Project project = newProject();
        Teacher teacher = newTeacher();
        teacher.setActive(false);

        when(repository.getOne(project.getId())).thenReturn(project);
        when(teacherService.findById(teacher.getId())).thenReturn(teacher);

        Project returned = service.setTeacher(project.getId(), teacher.getId());
        assertNotNull(returned);
    }

    @Test
    public void setStatus_shouldSucceed() {
        Project project = newProject();
        Status status = project.getStatus();

        when(repository.getOne(project.getId())).thenReturn(project);
        when(repository.save(project)).thenReturn(project);

        Project returned = service.setStatus(project.getId(), status);
        assertNotNull(returned);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void setStatus_shouldFail() {
        Project project = newProject();
        Status status = project.getStatus();
        service.setStatus(project.getId(), status);
    }

    @Test
    public void removeStudents_shouldSucceed() {
        Project project = newProject();
        Student student = newStudent();

        when(repository.getOne(project.getId())).thenReturn(project);
        when(studentService.findById(student.getId())).thenReturn(student);
        when(repository.save(project)).thenReturn(project);

        Project returned = service.removeStudents(project.getId(), student.getId());
        assertNotNull(returned);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void removeStudents_shouldFail_projectNotFound() {
       service.removeStudents(1L, 1L);
    }

    @Test(expected = StudentNotFoundException.class)
    public void removeStudents_shouldFail_studentNotFound() {
        Project project = newProject();
        Student student = newStudent();
        when(repository.getOne(project.getId())).thenReturn(project);
        service.removeStudents(project.getId(), student.getId());
    }

    @Test
    public void getProjectByTeacher_shouldSucceed() {
        List<Project> projectList = Lists.newArrayList(
                newProject(1L),
                newProject(2L),
                newProject(3L));

        when(repository.findByTeacherId(projectList.get(0).getTeacher().getId())).thenReturn(projectList);

        List<Project> returned = service.getProjectByTeacher(projectList.get(0).getTeacher().getId());
        assertEquals(projectList.size(), returned.size());
    }

    @Test
    public void getProjectByStudent_shouldSucceed() {
        List<Project> projectList = Lists.newArrayList(
                newProject(1L),
                newProject(2L),
                newProject(3L));

        when(repository.findByStudentsId(projectList.get(0).getStudentResponsible().getId())).thenReturn(projectList);

        List<Project> returned = service.getProjectByStudent(projectList.get(0).getStudentResponsible().getId());
        assertEquals(projectList.size(), returned.size());
    }

    @Test
    public void getProjectByEntrepreneur_shouldSucceed() {
        List<Project> projectList = Lists.newArrayList(
                newProject(1L),
                newProject(2L),
                newProject(3L));

        when(repository.findByEntrepreneurId(projectList.get(0).getEntrepreneur().getId())).thenReturn(projectList);

        List<Project> returned = service.getProjectByEntrepreneur(projectList.get(0).getEntrepreneur().getId());
        assertEquals(projectList.size(), returned.size());
    }

    @Test
    public void setSolution_shouldSucceed() {
        Project project = newProject();
        Deliver deliver = newDeliver();

        when(repository.getOne(project.getId())).thenReturn(project);
        when(repository.save(project)).thenReturn(project);

        Project returned = service.setSolution(project.getId(), deliver);
        assertNotNull(returned);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void setSolution_shouldFail() {
        Deliver deliver = newDeliver();
        service.setSolution(1L, deliver);
    }

    @Test
    public void setMeetingPossibleDate_shouldSucceed() {
        Project project = newProject();
        List<Date> possibleDates = project.getMeeting().getPossibleDate();

        when(repository.getOne(project.getId())).thenReturn(project);
        when(repository.save(project)).thenReturn(project);

        Project returned = service.setMeetingPossibleDate(possibleDates, project.getId());
        assertNotNull(returned);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void setMeetingPossibleDate_shouldFail_projectNotFound() {
        Project project = newProject();
        List<Date> possibleDates = project.getMeeting().getPossibleDate();
        service.setMeetingPossibleDate(possibleDates, project.getId());
    }

    @Test
    public void setMeetingChosenDate_shouldSucceed() {
        Project project = newProject();

        when(repository.getOne(project.getId())).thenReturn(project);
        when(repository.save(project)).thenReturn(project);

        Project returned = service.setMeetingChosenDate(project.getMeeting().getPossibleDate().get(0).getId(), project.getId());
        assertNotNull(returned);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void setMeetingChosenDate_shouldFail_projectNotFound() {
        Project project = newProject();
        service.setMeetingChosenDate(project.getMeeting().getPossibleDate().get(0).getId(), project.getId());
    }

    @Test(expected = DateDoesNotExistsException.class)
    public void setMeetingChosenDate_shouldFail_chosenDateNotFound() {
        Project project = newProject();

        when(repository.getOne(project.getId())).thenReturn(project);

        Project returned = service.setMeetingChosenDate(123L, project.getId());
        assertNotNull(returned);
    }
}









































