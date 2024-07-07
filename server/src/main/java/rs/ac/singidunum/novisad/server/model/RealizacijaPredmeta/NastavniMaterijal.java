package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NastavniMaterijal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;
    private LocalDateTime godinaIzdavanja;
    @OneToMany
    private Set<Nastavnik> autori;
    @OneToOne
    private Fajl fajl;
}
