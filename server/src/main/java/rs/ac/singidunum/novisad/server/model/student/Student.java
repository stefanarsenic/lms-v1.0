package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.*;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Student extends RegistrovaniKorisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private GodinaStudija godinaStudija;

    public Student() {
        super();
    }

    public Student(Long id, String korisnickoIme, String lozinka, Long jmbg, String ime, String prezime, String email, Adresa adresa, LocalDateTime datumRodjenja, Set<PravoPristupa> pravoPristupaSet, Long id1, GodinaStudija godinaStudija) {
        super(id, korisnickoIme, lozinka, jmbg, ime, prezime, email, adresa, datumRodjenja, pravoPristupaSet);
        this.id = id1;
        this.godinaStudija = godinaStudija;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
