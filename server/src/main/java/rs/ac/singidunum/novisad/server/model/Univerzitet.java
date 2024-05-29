package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Univerzitet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    @OneToOne
    private Adresa adresa;
    private LocalDateTime datumOsnovanja;


    @OneToMany(mappedBy = "univerzitet",cascade = CascadeType.ALL)
    private Set<Fakultet> fakulteti;

    @OneToOne
    private Nastavnik rektor;


    public Univerzitet() {
    }

    public Univerzitet(Long id, String naziv, Adresa adresa, LocalDateTime datumOsnovanja, Set<Fakultet> fakulteti, Nastavnik rektor) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.datumOsnovanja = datumOsnovanja;
        this.fakulteti = fakulteti;
        this.rektor = rektor;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
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

    public LocalDateTime getDatumOsnovanja() {
        return datumOsnovanja;
    }

    public void setDatumOsnovanja(LocalDateTime datumOsnovanja) {
        this.datumOsnovanja = datumOsnovanja;
    }

    public Set<Fakultet> getFakulteti() {
        return fakulteti;
    }

    public void setFakulteti(Set<Fakultet> fakulteti) {
        this.fakulteti = fakulteti;
    }

    public Nastavnik getRektor() {
        return rektor;
    }

    public void setRektor(Nastavnik rektor) {
        this.rektor = rektor;
    }
}
