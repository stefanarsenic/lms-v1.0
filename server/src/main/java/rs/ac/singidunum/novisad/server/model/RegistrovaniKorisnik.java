package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class RegistrovaniKorisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String korisnickoIme;
    private String lozinka;

    private Long jmbg;
    private String ime;
    private String prezime;

    private String email;
    @OneToOne
    private Adresa adresa;

    private LocalDateTime datumRodjenja;

    @OneToMany(mappedBy = "vlasnik")
    private Set<PravoPristupa> pravoPristupaSet=new HashSet<>();

    public RegistrovaniKorisnik() {
    }

    public RegistrovaniKorisnik(Long id, String korisnickoIme, String lozinka, Long jmbg, String ime, String prezime, String email, Adresa adresa, LocalDateTime datumRodjenja, Set<PravoPristupa> pravoPristupaSet) {
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.jmbg = jmbg;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.adresa = adresa;
        this.datumRodjenja = datumRodjenja;
        this.pravoPristupaSet = pravoPristupaSet;
    }

    public Long getJmbg() {
        return jmbg;
    }

    public void setJmbg(Long jmbg) {
        this.jmbg = jmbg;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public LocalDateTime getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDateTime datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
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
