package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String korisnickoIme;
    private String lozinka;

    @OneToMany(mappedBy = "vlasnik")
    private Set<PravoPristupa> pravoPristupaSet=new HashSet<>();

    public Korisnik() {
    }

    public Korisnik(Long id, String korisnickoIme, String lozinka) {
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Set<PravoPristupa> getPravoPristupaSet() {
        return pravoPristupaSet;
    }

    public void setPravoPristupaSet(Set<PravoPristupa> pravoPristupaSet) {
        this.pravoPristupaSet = pravoPristupaSet;
    }
}
