package br.gov.sp.fatec.entrepreneur.exception;

import static java.lang.String.format;

public class EntrepreneurException {

    public static class EntrepreneurNotFoundException extends RuntimeException {
        public EntrepreneurNotFoundException (Long id) {
            super(format("Entrepreneur with id %d does not exists", id));
        }
    }
}
