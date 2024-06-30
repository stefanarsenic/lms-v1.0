package rs.ac.singidunum.novisad.server.model.korisnik;

import jakarta.persistence.Entity;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Administrator  extends RegistrovaniKorisnik {

    public Administrator() {
        super();
    }

    public Administrator(Long id, String korisnickoIme, String lozinka, Long jmbg, String ime, String prezime, String email, Adresa adresa, LocalDateTime datumRodjenja, Set<PravoPristupa> pravoPristupaSet) {
        super(id, korisnickoIme, lozinka, jmbg, ime, prezime, email, adresa, datumRodjenja, pravoPristupaSet);
    }
}