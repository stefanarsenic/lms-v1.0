package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.util.Set;


//TODO:Proveriti za svaku kolonu da ne sme biti null itd (za sve entitete)
@Entity
public class Fakultet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    @OneToOne
    private Adresa adresa;

    @ManyToOne
    private Univerzitet univerzitet;

    @OneToOne
    private Nastavnik dekan;

    @OneToMany(mappedBy = "fakultet",cascade = CascadeType.ALL)
    private Set<StudijskiProgram> studijskiProgrami;

    public Fakultet() {
    }

    public Fakultet(Long id, String naziv, Adresa adresa, Univerzitet univerzitet, Nastavnik dekan, Set<StudijskiProgram> studijskiProgrami) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.univerzitet = univerzitet;
        this.dekan = dekan;
        this.studijskiProgrami = studijskiProgrami;
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

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public Univerzitet getUniverzitet() {
        return univerzitet;
    }

    public void setUniverzitet(Univerzitet univerzitet) {
        this.univerzitet = univerzitet;
    }

    public Nastavnik getDekan() {
        return dekan;
    }

    public void setDekan(Nastavnik dekan) {
        this.dekan = dekan;
    }

    public Set<StudijskiProgram> getStudijskiProgrami() {
        return studijskiProgrami;
    }

    public void setStudijskiProgrami(Set<StudijskiProgram> studijskiProgrami) {
        this.studijskiProgrami = studijskiProgrami;
    }
}
