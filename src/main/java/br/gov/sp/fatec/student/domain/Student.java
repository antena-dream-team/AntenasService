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
    @ManyToMany(mappedBy = "students")
    private List<Project> projects = new LinkedList<>();

    public List<Project> getProjects() {
        return projects;
    }
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
