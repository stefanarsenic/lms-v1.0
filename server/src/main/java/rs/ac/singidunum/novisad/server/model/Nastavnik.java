package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Nastavnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String biografija;
    private String jmbg;

    @OneToMany(mappedBy = "nastavnik")
    private Set<Zvanje> zvanja;

    public Nastavnik() {
    }

    public Nastavnik(Long id, String naziv, String biografija, String jmbg, Set<Zvanje> zvanja) {
        this.id = id;
        this.ime = naziv;
        this.biografija = biografija;
        this.jmbg = jmbg;
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

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public Set<Zvanje> getZvanja() {
        return zvanja;
    }

    public void setZvanja(Set<Zvanje> zvanja) {
        this.zvanja = zvanja;
    }
}
