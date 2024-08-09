package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;

import java.util.Set;

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
    @OneToMany(mappedBy = "realizacijaPredmeta")
    private Set<TerminNastave> terminiNastave;
    @OneToMany(mappedBy = "realizacijaPredmeta")
    private Set<EvaluacijaZnanja> evaluacijeZnanja;

    @OneToMany(mappedBy ="realizacijaPredmeta" )
    private Set<Obavestenje> obavestenja;
}
