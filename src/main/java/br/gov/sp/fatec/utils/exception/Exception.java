package br.gov.sp.fatec.utils.exception;

public class Exception extends Throwable {

    public static class SendEmailFailedException extends RuntimeException {
        public SendEmailFailedException() {
            super("Failed to send email");
        }
    }

    public static class CreateUserFailed extends RuntimeException {
        public CreateUserFailed() {
            super("There is more than one user with this email");
        }
    }
}


