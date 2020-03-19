package br.gov.sp.fatec.teacher.exception;

import static java.lang.String.format;

public class TeacherException {
    public static class TeacherNotFoundException extends RuntimeException {
        public TeacherNotFoundException(Long id) {
            super(format("Teacher with id %d does not exists", id));
        }
    }
}
