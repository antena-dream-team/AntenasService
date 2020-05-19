package br.gov.sp.fatec.entrepreneur.domain;

import br.gov.sp.fatec.User.Domain.User;
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
@Table(name = "entrepreneur")
public class Entrepreneur extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private Long id;

    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private String cpf;

    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private String telephone;

    @JsonView({EntrepreneurView.Entrepreneur.class, ProjectView.Project.class})
    private String company;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
