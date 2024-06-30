package rs.ac.singidunum.novisad.server.model.nastavnik;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.nastavnik.NacunaOblast;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipZvanja;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Zvanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datumIzbora;
    private LocalDateTime datumPrestanka;

    @ManyToOne
    private Nastavnik nastavnik;
    @OneToOne
    private TipZvanja tipZvanja;
    @OneToOne
    private NacunaOblast nacunaOblast;
}
