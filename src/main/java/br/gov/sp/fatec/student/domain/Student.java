package br.gov.sp.fatec.student.domain;

import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.student.view.StudentView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class, StudentView.Student.class})
    private Long id;

    @JsonView({ProjectView.Project.class, StudentView.Student.class})
    private String email;

    @JsonView({ProjectView.Project.class, StudentView.Student.class})
    private String password;

    @JsonView({ProjectView.Project.class, StudentView.Student.class})
    private String name;

    @JsonView({ProjectView.Project.class, StudentView.Student.class})
    private boolean active;

    @JsonView({StudentView.Student.class})
    @ManyToMany(mappedBy = "students")
    private List<br.gov.sp.fatec.project.domain.Project> projects = new LinkedList<>();
}
