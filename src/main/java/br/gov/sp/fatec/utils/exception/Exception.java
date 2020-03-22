package br.gov.sp.fatec.utils.exception;

import static java.lang.String.format;

public class Exception extends Throwable {

    public static class SendEmailFailedException extends RuntimeException {
        public SendEmailFailedException() {
            super("Failed to send email");
        }
    }
}


