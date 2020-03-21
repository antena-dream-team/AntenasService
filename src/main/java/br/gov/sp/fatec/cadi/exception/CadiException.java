package br.gov.sp.fatec.cadi.exception;

import static java.lang.String.format;

public class CadiException {

    public static class CadiNotFoundException extends RuntimeException {
        public CadiNotFoundException(Long id) {
            super(format("Cadi with id %d does not exists", id));
        }
    }

    public static class CadiLoginFailed extends RuntimeException {
        public CadiLoginFailed() {
            super(format("Cadi - login failed"));
        }
    }

    public static class CadiInactiveException extends RuntimeException {
        public CadiInactiveException(Long id) {
            super(format("Cadi with id %d is inactive", id));
        }
    }

}
