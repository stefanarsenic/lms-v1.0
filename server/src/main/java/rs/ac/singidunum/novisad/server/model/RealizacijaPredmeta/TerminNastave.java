package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipNastave;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TerminNastave {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime vremePocetka;
    private LocalDateTime vremeZavrsetka;
    @OneToOne
    private Ishod ishod; //obrazovni cilj dodati u ishod koji je nullable
    @OneToOne
    private TipNastave tipNastave;
    @OneToOne
    private NastavniMaterijal nastavniMaterijal;
}
