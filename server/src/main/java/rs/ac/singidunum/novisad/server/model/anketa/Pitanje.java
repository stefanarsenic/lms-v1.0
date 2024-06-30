package rs.ac.singidunum.novisad.server.model.anketa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pitanje {

    @Id
    @GeneratedValue
    private Long id;
    private String pitanje;
    @OneToOne
    private TipOdgovora tipOdgovora;
    @ManyToOne
    private Anketa anketa;
}
