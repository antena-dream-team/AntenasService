package br.gov.sp.fatec.User.Domain;

import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.teacher.view.TeacherView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class, TeacherView.Teacher.class})
    private Long id;

    @JsonView({ProjectView.Project.class, TeacherView.Teacher.class})
    protected String email;

    protected String password;

    @JsonView({ProjectView.Project.class, TeacherView.Teacher.class})
    protected String name;

    @JsonView({ProjectView.Project.class, TeacherView.Teacher.class})
    protected Boolean active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
