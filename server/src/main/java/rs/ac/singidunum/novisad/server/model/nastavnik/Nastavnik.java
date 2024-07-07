package rs.ac.singidunum.novisad.server.model.nastavnik;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Nastavnik extends RegistrovaniKorisnik {

    private String ime;
    private String biografija;
    @OneToMany(mappedBy = "nastavnik")
    private Set<Zvanje> zvanja;
}
