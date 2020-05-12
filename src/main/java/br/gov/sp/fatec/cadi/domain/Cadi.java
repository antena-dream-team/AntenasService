package br.gov.sp.fatec.cadi.domain;

import br.gov.sp.fatec.cadi.view.CadiView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cadi")
public class Cadi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CadiView.Cadi.class})
    private Long id;
    
    @JsonView({CadiView.Cadi.class})
    private String email;

    private String password;

    @JsonView({CadiView.Cadi.class})
    private String name;

    @JsonView({CadiView.Cadi.class})
    private String cpf;

    @JsonView({CadiView.Cadi.class})
    private String position;

    @JsonView({CadiView.Cadi.class})
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
