package br.gov.sp.fatec.project.domain;

import br.gov.sp.fatec.project.view.ProjectView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class})
    private Long id;

    @JsonView({ProjectView.Project.class})
    private boolean denied;

    @JsonView({ProjectView.Project.class})
    private String reason;
}
