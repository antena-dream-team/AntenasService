package br.gov.sp.fatec.security.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorization")
public class Authorization implements GrantedAuthority {

    private static final long serialVersionUID = 3078636239920155012L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @JsonView({View.UsuarioCompleto.class, View.Autorizacao.class})
    private Long id;

    @Column(unique=true, length = 20, nullable = false)
//    @JsonView({View.UsuarioResumo.class, View.UsuarioResumoAlternativo.class, View.Autorizacao.class})
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return name;
    }

    public void setNome(String nome) {
        this.name = nome;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    public void setAuthority(String authority) {
        this.name = authority;
    }

}
