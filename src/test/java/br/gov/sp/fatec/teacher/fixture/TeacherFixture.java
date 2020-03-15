package br.gov.sp.fatec.teacher.fixture;

import br.gov.sp.fatec.teacher.domain.Teacher;

public class TeacherFixture {

    private static final String EMAIL = "email@teste.com";
    private static final String NAME = "test";
    private static final String PASSWORD = "test";
    private static final Long ID = 1L;
    private static final Boolean ACTIVE = true;

    public static Teacher newTeacher(Long id, boolean active) {
        return Teacher.builder()
                .active(active)
                .email(EMAIL)
                .name(NAME)
                .password(PASSWORD)
                .id(id)
                .build();
    }

    public static Teacher newTeacher() {
        return Teacher.builder()
                .active(ACTIVE)
                .email(EMAIL)
                .name(NAME)
                .password(PASSWORD)
                .id(ID)
                .build();
    }
}
