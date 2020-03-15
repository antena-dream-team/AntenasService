package br.gov.sp.fatec.student.exception;

import static java.lang.String.format;

public class StudentException {

    public static class StudentInactiveException extends Exception {
        public StudentInactiveException(Long id) {
            super(format("The student with the id '%d' is inactive", id));
        }
    }

    public static class StudentNotFoundException extends Exception {
        public StudentNotFoundException(Long id) {
            super(format("The student with the id '%d' does not exists", id));
        }
    }
}
