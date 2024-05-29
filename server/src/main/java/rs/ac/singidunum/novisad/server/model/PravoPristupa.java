package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

@Entity
public class PravoPristupa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;

    @ManyToOne()
    private Korisnik vlasnik;

    public PravoPristupa(Long id, String naziv, Korisnik vlasnik) {
        this.id = id;
        this.naziv = naziv;
        this.vlasnik = vlasnik;
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

    public Korisnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Korisnik vlasnik) {
        this.vlasnik = vlasnik;
    }
}