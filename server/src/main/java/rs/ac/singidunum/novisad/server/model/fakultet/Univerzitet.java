package rs.ac.singidunum.novisad.server.model.fakultet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.fakultet.Fakultet;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Univerzitet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;
    private LocalDateTime datumOsnivanja;
    @ManyToOne
    private Adresa adresa;
    @OneToMany(mappedBy = "univerzitet",cascade = CascadeType.ALL)
    private Set<Fakultet> fakulteti;
    @OneToOne
    private Nastavnik rektor;
}
