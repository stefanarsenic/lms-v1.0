package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EvaluacijaZnanja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime vremePocetka;
    private LocalDateTime vremeZavrsetka;
    private Integer bodovi;
    @OneToOne
    private Ishod ishod;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Fajl instrumentEvaluacije;
    @ManyToOne
    private TipEvaluacije tipEvaluacije;
    @ManyToOne
    private RealizacijaPredmeta realizacijaPredmeta;
}
