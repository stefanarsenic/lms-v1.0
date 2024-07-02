package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
    @GeneratedValue
    private Long id;
    @OneToOne
    private Predmet predmet;
    @OneToOne
    private TerminNastave terminNastave;
    @OneToOne
    private EvaluacijaZnanja evaluacijaZnanja;
}
