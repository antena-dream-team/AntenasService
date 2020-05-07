package br.gov.sp.fatec.student.domain;

import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.student.view.StudentView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    private String password;

    @JsonView({ProjectView.Project.class, StudentView.Student.class})
    private String name;

    @JsonView({ProjectView.Project.class, StudentView.Student.class})
    private boolean active;

    @JsonView({StudentView.Student.class})
    @ManyToMany(mappedBy = "students")
    private List<br.gov.sp.fatec.project.domain.Project> projects = new LinkedList<>();

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
