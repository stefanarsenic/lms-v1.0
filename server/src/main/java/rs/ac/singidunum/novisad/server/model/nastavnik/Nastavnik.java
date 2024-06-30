package rs.ac.singidunum.novisad.server.model.nastavnik;

import jakarta.persistence.*;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Nastavnik extends RegistrovaniKorisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String biografija;

    @OneToMany(mappedBy = "nastavnik")
    private Set<Zvanje> zvanja;

    public Nastavnik() {
        super();
    }

    public Nastavnik(Long id, String korisnickoIme, String lozinka, Long jmbg, String ime, String prezime, String email, Adresa adresa, LocalDateTime datumRodjenja, Set<PravoPristupa> pravoPristupaSet, Long id1, String ime1, String biografija, Set<Zvanje> zvanja) {
        super(id, korisnickoIme, lozinka, jmbg, ime, prezime, email, adresa, datumRodjenja, pravoPristupaSet);
        this.id = id1;
        this.ime = ime1;
        this.biografija = biografija;
        this.zvanja = zvanja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getBiografija() {
        return biografija;
    }

    public void setBiografija(String biografija) {
        this.biografija = biografija;
    }


    public Set<Zvanje> getZvanja() {
        return zvanja;
    }

    public void setZvanja(Set<Zvanje> zvanja) {
        this.zvanja = zvanja;
    }
}
