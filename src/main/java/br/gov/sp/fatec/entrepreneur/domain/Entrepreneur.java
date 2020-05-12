package br.gov.sp.fatec.entrepreneur.domain;

import br.gov.sp.fatec.entrepreneur.view.EntrepreneurView;
import br.gov.sp.fatec.project.view.ProjectView;
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
@Table(name = "entrepreneur")
public class Entrepreneur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private Long id;
    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private String email;

    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private String password;

    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private String name;

    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private String cpf;

    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private String telephone;

    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private boolean active;

    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private String company;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
