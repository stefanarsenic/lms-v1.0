package rs.ac.singidunum.novisad.server.model.student;

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
public class Student extends RegistrovaniKorisnik {
    private String jmbg;
    private LocalDateTime datumRodjenja;
    @ManyToOne
    private Adresa adresa;
}
