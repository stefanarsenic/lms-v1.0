package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;


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
public class RealizacijaPredmeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Predmet predmet;
    @OneToOne
    private TerminNastave terminNastave;
    @OneToOne
    private EvaluacijaZnanja evaluacijaZnanja;
}
