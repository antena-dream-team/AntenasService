package br.gov.sp.fatec.project.fixture;

import br.gov.sp.fatec.project.domain.*;
import br.gov.sp.fatec.student.domain.Student;

import java.util.LinkedList;
import java.util.List;

import static br.gov.sp.fatec.entrepreneur.fixture.EntrepreneurFixture.newEntrepreneur;
import static br.gov.sp.fatec.student.fixture.StudentFixture.newStudent;
import static br.gov.sp.fatec.student.fixture.StudentFixture.newStudentNoProject;
import static br.gov.sp.fatec.teacher.fixture.TeacherFixture.newTeacher;

public class ProjectFixture {

    private static final String COMPLETE_DESCRIPTION = "projeto que faz uma banana de cimento comestível para combater carrapatos em cinemas";
    private static final String TECHNOLOGY_DESCRIPTION = "Uma banana feita de cimento que pode ser ingerido que persegue carrapatos e batalha com eles, para expula-los dos cinemas";
    private static final String SHORT_DESCRIPTION = "banana de cimento que combate carrapato";
    private static final String TITLE = "destruidora de carrapato";
    private static final String CLOUD_LINK = "cloud.com";
    private static final String REPOSITORY_LINK = "repository.com";
    private static final String COMMENT = "Comentario da entrega";
    private static final Long ID = 1L;

    public static Project newProject() {
        return  Project.builder()
                .completeDescription(COMPLETE_DESCRIPTION)
                .entrepreneur(newEntrepreneur())
                .id(ID)
                .deliver(getDeliver())
                .meeting(newMeeting())
                .shortDescription(SHORT_DESCRIPTION)
                .studentResponsible(newStudentNoProject(ID, true))
                .students(getStudents())
                .teacher(newTeacher())
                .technologyDescription(TECHNOLOGY_DESCRIPTION)
                .title(TITLE)
                .build();
    }

    public static Project newProject(Long id) {
        return  Project.builder()
                .completeDescription(COMPLETE_DESCRIPTION)
                .entrepreneur(newEntrepreneur())
                .id(id)
                .deliver(getDeliver())
                .meeting(newMeeting())
                .shortDescription(SHORT_DESCRIPTION)
                .studentResponsible(newStudentNoProject(ID, true))
                .students(getStudents())
                .teacher(newTeacher())
                .technologyDescription(TECHNOLOGY_DESCRIPTION)
                .title(TITLE)
                .build();
    }

    public static Deliver newDeliver() {
        return Deliver.builder()
                .id(ID)
                .cloudLink(CLOUD_LINK)
                .comment(COMMENT)
                .repositoryLink(REPOSITORY_LINK)
                .studentResponsible(newStudent())
                .students(getStudents())
                .build();
    }

    public static Deliver newDeliver(Long id) {
        return Deliver.builder()
                .id(id)
                .cloudLink(CLOUD_LINK)
                .comment(COMMENT)
                .repositoryLink(REPOSITORY_LINK)
                .studentResponsible(newStudentNoProject())
                .students(getStudents())
                .build();
    }

    public static List<Deliver> getDeliver() {
        List<Deliver> deliverList = new LinkedList<>();

        for (long i = 0; i < 3; i++) {
            deliverList.add(newDeliver(i));
        }

        return deliverList;
    }

    public static Meeting newMeeting() {
        return Meeting.builder()
                .address(newAddress())
                .chosenDate(new java.util.Date())
                .possibleDate(getPossibleDate())
                .id(ID)
                .build();
    }

    public static Address newAddress() {
        return Address.builder()
                .city("SJC")
                .neighborhood("vila do chaves")
                .place("vila")
                .street("rua dos bobos")
                .zip("1234-123")
                .number(0)
                .id(ID)
                .build();
    }

    public static Date newDate(Long id) {
        return Date.builder()
                .id(id)
                .dateTime(new java.util.Date())
                .build();
    }

    public static List<Date> getPossibleDate() {
        List<Date> dateList = new LinkedList<>();

        for (long i = 0; i < 3; i++) {
            dateList.add(newDate(i));
        }

        return dateList;
    }

    public static List<Student> getStudents() {
        List<Student> studentList = new LinkedList<>();
        studentList.add(newStudentNoProject(1L, true));
        return studentList;
    }
}
