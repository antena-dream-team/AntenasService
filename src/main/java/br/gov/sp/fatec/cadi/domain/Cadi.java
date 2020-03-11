package br.gov.sp.fatec.cadi.domain;

import br.gov.sp.fatec.cadi.view.CadiView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cadi")
public class Cadi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CadiView.Cadi.class})
    private Long id;
    
    @JsonView({CadiView.Cadi.class})
    private String email;

    @JsonView({CadiView.Cadi.class})
    private String password;

    @JsonView({CadiView.Cadi.class})
    private String name;

    @JsonView({CadiView.Cadi.class})
    private String cpf;

    @JsonView({CadiView.Cadi.class})
    private String position;

    @JsonView({CadiView.Cadi.class})
    private boolean active;
}
