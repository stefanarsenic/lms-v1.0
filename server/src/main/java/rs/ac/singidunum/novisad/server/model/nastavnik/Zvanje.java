package rs.ac.singidunum.novisad.server.model.nastavnik;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private NaucnaOblast naucnaOblast;
}
