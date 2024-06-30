package rs.ac.singidunum.novisad.server.model.nastavnik;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AngazovanjeNastavnika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer brojCasova;
    @OneToOne
    private Nastavnik nastavnik;
    @OneToOne
    private Predmet predmet;
    @OneToOne
    private TipNastave tipNastave;
}
