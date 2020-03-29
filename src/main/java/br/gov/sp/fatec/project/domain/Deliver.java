package br.gov.sp.fatec.project.domain;

import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.student.domain.Student;
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
@Table(name = "deliver")
public class Deliver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class})
    private Long id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class})
    private String repositoryLink;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class})
    private String cloudLink;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class})
    private String comment;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class})
    @OneToOne(cascade = CascadeType.ALL)
    private Student studentResponsible;

    @JsonView({ProjectView.Project.class})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "deliver_team",
            joinColumns = @JoinColumn(name = "deliver_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students = new ArrayList<>();

    @JsonView({ProjectView.Project.class})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_deliver",
            joinColumns = @JoinColumn(name = "deliver_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects = new ArrayList<>();
}
