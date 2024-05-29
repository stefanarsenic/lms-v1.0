package rs.ac.singidunum.novisad.server.model;


import jakarta.persistence.*;

@Entity
public class Mesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    @ManyToOne
    private Drzava drzava;

    public Mesto() {
    }

    public Mesto(Long id, String naziv, Drzava drzava) {
        this.id = id;
        this.naziv = naziv;
        this.drzava = drzava;
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

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }
}
