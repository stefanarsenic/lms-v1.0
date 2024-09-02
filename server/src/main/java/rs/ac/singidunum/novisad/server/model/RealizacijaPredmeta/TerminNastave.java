package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;

import jakarta.persistence.*;
import lombok.*;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class TerminNastave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime vremePocetka;
    private LocalDateTime vremeZavrsetka;
    @OneToOne
    private Ishod ishod;
    @ManyToOne
    private TipNastave tipNastave;
    @ManyToOne
    private RealizacijaPredmeta realizacijaPredmeta;
}
