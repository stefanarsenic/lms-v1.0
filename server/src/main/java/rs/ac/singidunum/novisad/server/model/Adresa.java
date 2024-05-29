package rs.ac.singidunum.novisad.server.model;


import jakarta.persistence.*;

@Entity
public class Adresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ulica;
    private String broj;

    @OneToOne
    private Mesto mesto;

    public Adresa() {
    }

    public Adresa(Long id, String ulica, String broj, Mesto mesto) {
        this.id = id;
        this.ulica = ulica;
        this.broj = broj;
        this.mesto = mesto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }
}
