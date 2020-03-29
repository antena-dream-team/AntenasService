package br.gov.sp.fatec.project.domain;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.student.domain.Student;
import br.gov.sp.fatec.student.view.StudentView;
import br.gov.sp.fatec.teacher.domain.Teacher;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class, StudentView.Student.class})
    private Long id;

    @JsonView({ProjectView.Project.class})
    private String title;

    @JsonView({ProjectView.Project.class})
    @Column(name = "short_description")
    private String shortDescription;

    @JsonView({ProjectView.Project.class})
    @Column(name = "complete_description")
    private String completeDescription;

    @JsonView({ProjectView.Project.class})
    @Column(name = "tecnology_description")
    private String technologyDescription;

    @JsonView({ProjectView.Project.class})
    @Column(name = "notes")
    private String notes;

    @JsonView({ProjectView.Project.class})
    private String progress;

    @JsonView({ProjectView.Project.class})
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    private Meeting meeting;

    @JsonView({ProjectView.Project.class})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cadi_id", referencedColumnName = "id")
    private Cadi cadi;

    @JsonView({ProjectView.Project.class})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @JsonView({ProjectView.Project.class})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entrepreneur_id", referencedColumnName = "id")
    private Entrepreneur entrepreneur;

    @JsonView({ProjectView.Project.class})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @JsonView({ProjectView.Project.class})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_student",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
   private List<Student> students = new ArrayList<>();

//    todo - testar sem o cascade dps
    @JsonView({ProjectView.Project.class})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_deliver",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "deliver_id"))
    private List<Deliver> deliver = new ArrayList<>();

    @JsonView({ProjectView.Project.class})
    @OneToOne(cascade = CascadeType.ALL)
    private Student studentResponsible;

    @CreatedDate
    @Column(nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();
}