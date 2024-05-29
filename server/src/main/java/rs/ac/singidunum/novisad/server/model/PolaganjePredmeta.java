package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

@Entity
public class PolaganjePredmeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer ocena;
    private Integer bodovi;
    private Integer brojPolaganja;
    @OneToOne
    private Student student;
    @OneToOne
    private Predmet predmet;

    public PolaganjePredmeta() {
    }

    public PolaganjePredmeta(Long id, Integer ocena, Integer bodovi, Integer brojPolaganja, Student student, Predmet predmet) {
        this.id = id;
        this.ocena = ocena;
        this.bodovi = bodovi;
        this.brojPolaganja = brojPolaganja;
        this.student = student;
        this.predmet = predmet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public Integer getBodovi() {
        return bodovi;
    }

    public void setBodovi(Integer bodovi) {
        this.bodovi = bodovi;
    }

    public Integer getBrojPolaganja() {
        return brojPolaganja;
    }

    public void setBrojPolaganja(Integer brojPolaganja) {
        this.brojPolaganja = brojPolaganja;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }
}
