package br.gov.sp.fatec.cadi.fixture;

import br.gov.sp.fatec.cadi.domain.Cadi;

import javax.persistence.Id;

public class CadiFixture {

    private static final Boolean ACTIVE = true;
    private static final String POSITION = "professor";
    private static final String CPF = "111.111.111-11";
    private static final String EMAIL = "email@teste.com";
    private static final String NAME = "test";
    private static final String PASSWORD = "test";
    private static final Long ID = 1l;

    public static Cadi newCadi() {
        return Cadi.builder()
                .active(ACTIVE)
                .cpf(CPF)
                .email(EMAIL)
                .id(ID)
                .position(POSITION)
                .name(NAME)
                .password(PASSWORD)
                .build();
    }

    public static Cadi newCadi(Long id, Boolean active) {
        return Cadi.builder()
                .active(active)
                .cpf(CPF)
                .email(EMAIL)
                .id(id)
                .position(POSITION)
                .name(NAME)
                .password(PASSWORD)
                .build();
    }
}
