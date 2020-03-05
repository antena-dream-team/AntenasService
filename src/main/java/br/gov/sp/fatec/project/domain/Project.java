package br.gov.sp.fatec.project.domain;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.teacher.domain.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "complete_description")
    private String completeDescription;

    @Column(name = "tecnology_description")
    private String tecnologyDescription;

    @Column(name = "external_link_1")
    private String externalLink1;

    @Column(name = "external_link_2")
    private String externalLink2;

    private String phase;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    private Meeting meeting;

//    @OneToOne // TODO - VERIFICAR OQ FAZER C ISSO
//    private Cadi cadi_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entrepreneur_id", referencedColumnName = "id")
    private Entrepreneur entrepreneur;

    private String key;

//    private Status status; // TODO - VERIFICAR SE O LIQUIBASE ESTA CRIANDO O STATUS

}