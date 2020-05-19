package br.gov.sp.fatec.teacher.domain;

import br.gov.sp.fatec.User.Domain.User;
import br.gov.sp.fatec.project.domain.Deliver;
import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.teacher.view.TeacherView;
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
@Table(name = "teacher")
public class Teacher extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class, TeacherView.Teacher.class})
    private Long id;

    @JsonView({ProjectView.Project.class})
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
