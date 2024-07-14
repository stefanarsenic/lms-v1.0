package rs.ac.singidunum.novisad.server.model.korisnik;

import jakarta.persistence.*;

@Entity
public class PravoPristupa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;

    @ManyToOne()
    private RegistrovaniKorisnik registrovaniKorisnik;

    public PravoPristupa(Long id, String naziv, RegistrovaniKorisnik vlasnik) {
        this.id = id;
        this.naziv = naziv;
        this.registrovaniKorisnik = vlasnik;
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

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public RegistrovaniKorisnik getRegistrovaniKorisnik() {
        return registrovaniKorisnik;
    }

    public void setRegistrovaniKorisnik(RegistrovaniKorisnik registrovaniKorisnik) {
        this.registrovaniKorisnik = registrovaniKorisnik;
    }
}
