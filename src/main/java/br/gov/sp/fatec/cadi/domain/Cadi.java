package br.gov.sp.fatec.cadi.domain;

import br.gov.sp.fatec.User.Domain.User;
import br.gov.sp.fatec.cadi.view.CadiView;
import br.gov.sp.fatec.project.view.ProjectView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cadi")
public class Cadi extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CadiView.Cadi.class})
    private Long id;

    @JsonView({CadiView.Cadi.class})
    private String cpf;

    @JsonView({CadiView.Cadi.class})
    private String position;

    @JsonView({ProjectView.Project.class})
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_cadi",
            joinColumns = @JoinColumn(name = "cadi_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> user = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
}
