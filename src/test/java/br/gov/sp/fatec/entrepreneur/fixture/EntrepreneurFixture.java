package br.gov.sp.fatec.entrepreneur.fixture;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;

public class EntrepreneurFixture {

    private static final Boolean ACTIVE = true;
    private static final String COMPANY = "empresinha";
    private static final String CPF = "111.111.111-11";
    private static final String EMAIL = "email@teste.com";
    private static final String NAME = "test";
    private static final String PASSWORD = "test";
    private static final String TELEPHONE = "(12) 91111-1111";
    private static final Long ID = 1l;

    public static Entrepreneur newEntrepreneur() {
        return Entrepreneur.builder()
                .active(ACTIVE)
                .company(COMPANY)
                .cpf(CPF)
                .email(EMAIL)
                .name(NAME)
                .password(PASSWORD)
                .telephone(TELEPHONE)
                .id(ID)
                .build();
    }

    public static Entrepreneur newEntrepreneur(Long id, Boolean active) {
        return Entrepreneur.builder()
                .active(active)
                .company(COMPANY)
                .cpf(CPF)
                .email(EMAIL)
                .name(NAME)
                .password(PASSWORD)
                .telephone(TELEPHONE)
                .id(id)
                .build();
    }
}
