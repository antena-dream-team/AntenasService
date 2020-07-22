package br.gov.sp.fatec.user.exception;

import static java.lang.String.format;

public class UserException {


    public static class userNotFoundException extends RuntimeException {
        public userNotFoundException(Long id) {
            super("User with id " + id + " not found.");
        }
    }
}
