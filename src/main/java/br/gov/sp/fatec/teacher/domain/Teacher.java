package br.gov.sp.fatec.teacher.domain;

import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.teacher.view.TeacherView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class, TeacherView.Teacher.class})
    private Long id;

    @JsonView({ProjectView.Project.class, TeacherView.Teacher.class})
    private String email;

    private String password;

    @JsonView({ProjectView.Project.class, TeacherView.Teacher.class})
    private String name;

    @JsonView({ProjectView.Project.class, TeacherView.Teacher.class})
    private Boolean active;

}
