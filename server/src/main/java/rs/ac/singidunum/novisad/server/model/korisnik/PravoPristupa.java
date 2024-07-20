package rs.ac.singidunum.novisad.server.model.korisnik;

import jakarta.persistence.*;

@Entity
public class PravoPristupa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Uloga uloga;

    @ManyToOne()
    private RegistrovaniKorisnik registrovaniKorisnik;

    public PravoPristupa(Long id, Uloga uloga, RegistrovaniKorisnik registrovaniKorisnik) {
        this.id = id;
        this.uloga = uloga;
        this.registrovaniKorisnik = registrovaniKorisnik;
    }

    public PravoPristupa() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    public RegistrovaniKorisnik getRegistrovaniKorisnik() {
        return registrovaniKorisnik;
    }

    public void setRegistrovaniKorisnik(RegistrovaniKorisnik registrovaniKorisnik) {
        this.registrovaniKorisnik = registrovaniKorisnik;
    }
}
