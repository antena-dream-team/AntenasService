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
}
