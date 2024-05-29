package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Drzava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    @OneToMany(mappedBy = "drzava",cascade = CascadeType.ALL)
    private Set<Mesto> mesta;

    public Drzava() {
    }

    public Drzava(Long id, String naziv, Set<Mesto> mesta) {
        this.id = id;
        this.naziv = naziv;
        this.mesta = mesta;
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

    public Set<Mesto> getMesta() {
        return mesta;
    }

    public void setMesta(Set<Mesto> mesta) {
        this.mesta = mesta;
    }
}
