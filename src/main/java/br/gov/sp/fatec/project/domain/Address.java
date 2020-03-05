package br.gov.sp.fatec.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String place;

    private int number;

    private String street;

    private String neighborhood;

    private String city;

    private String zip;

    @OneToMany(mappedBy = "id", targetEntity = Meeting.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Meeting> meeting = new ArrayList<>();
}
