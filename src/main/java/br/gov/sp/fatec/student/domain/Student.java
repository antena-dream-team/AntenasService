package br.gov.sp.fatec.student.domain;

import br.gov.sp.fatec.User.Domain.User;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.student.view.StudentView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class, StudentView.Student.class})
    private Long id;

    @JsonView({StudentView.Student.class})
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> user = new ArrayList<>();

    private List<br.gov.sp.fatec.project.domain.Project> projects = new LinkedList<>();

    public List<Project> getProjects() {
        return projects;
    }
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
