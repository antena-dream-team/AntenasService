package br.gov.sp.fatec.project.domain;

import br.gov.sp.fatec.project.view.ProjectView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.Project.class})
    private Long id;

    @JsonView({ProjectView.Project.class})
    @Column(name = "chosen_date")
    private java.util.Date chosenDate;

    @JsonView({ProjectView.Project.class})
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @JsonView({ProjectView.Project.class})
    @ManyToMany
    @JoinTable(name="meeting_possible_date",
            joinColumns=@JoinColumn(name="meeting_id"),
            inverseJoinColumns=@JoinColumn(name="possible_date_id"))
    private List<Date> possibleDate;
}
