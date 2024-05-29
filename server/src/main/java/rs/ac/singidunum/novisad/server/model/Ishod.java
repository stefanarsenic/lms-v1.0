package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

@Entity
public class Ishod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(nullable = false)
    private String opis;
    @ManyToOne
    private Predmet predmet;

    public Ishod() {
    }

    public Ishod(Long id, String opis, Predmet predmet) {
        this.id = id;
        this.opis = opis;
        this.predmet = predmet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }
}
