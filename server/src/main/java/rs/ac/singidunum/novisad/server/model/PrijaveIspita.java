package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class PrijaveIspita {

    @Id
    @GeneratedValue
    private Long id;
    private Integer brojPrijave;
    @OneToOne
    private Student student;
    @OneToOne
    private Predmet predmet;

    public PrijaveIspita() {
    }

    public PrijaveIspita(Long id, Integer brojPrijave, Student student, Predmet predmet) {
        this.id = id;
        this.brojPrijave = brojPrijave;
        this.student = student;
        this.predmet = predmet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBrojPrijave() {
        return brojPrijave;
    }

    public void setBrojPrijave(Integer brojPrijave) {
        this.brojPrijave = brojPrijave;
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
