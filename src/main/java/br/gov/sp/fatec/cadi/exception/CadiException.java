package br.gov.sp.fatec.cadi.exception;

import static java.lang.String.format;

public class CadiException {

    public static class CadiNotFoundException extends RuntimeException {
        public CadiNotFoundException(Long id) {
            super(format("Cadi with id %d does not exists", id));
        }
    }
}
