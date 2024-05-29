package rs.ac.singidunum.novisad.server.model;


import jakarta.persistence.*;

import java.util.Set;

@Entity
public class StudijskiProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    private String opis;

    @ManyToOne
    private Fakultet fakultet;

    @OneToOne
    private Nastavnik rukovodilac;

    @OneToMany //TODO:Pitati asistenra za ovo
    private Set<GodinaStudija> godineStudija;

    public StudijskiProgram() {
    }

    public StudijskiProgram(Long id, String naziv, String opis, Fakultet fakultet, Nastavnik rukovodilac, Set<GodinaStudija> godineStudija) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.fakultet = fakultet;
        this.rukovodilac = rukovodilac;
        this.godineStudija = godineStudija;
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Fakultet getFakultet() {
        return fakultet;
    }

    public void setFakultet(Fakultet fakultet) {
        this.fakultet = fakultet;
    }

    public Nastavnik getRukovodilac() {
        return rukovodilac;
    }

    public void setRukovodilac(Nastavnik rukovodilac) {
        this.rukovodilac = rukovodilac;
    }

    public Set<GodinaStudija> getGodineStudija() {
        return godineStudija;
    }

    public void setGodineStudija(Set<GodinaStudija> godineStudija) {
        this.godineStudija = godineStudija;
    }
}
