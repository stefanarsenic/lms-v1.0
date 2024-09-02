package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NastavnikNaRealizaciji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer brojCasova;
    @ManyToOne
    private Nastavnik nastavnik;
    @ManyToOne
    private TipNastave tipNastave;
    @ManyToOne
    private RealizacijaPredmeta realizacijaPredmeta;
}
