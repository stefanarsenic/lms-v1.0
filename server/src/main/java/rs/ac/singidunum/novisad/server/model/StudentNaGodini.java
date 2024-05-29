package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class StudentNaGodini {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime datumUpisa;
    private String brojIndeksa;
    @OneToOne
    private Student student;
    @ManyToOne
    private GodinaStudija godinaStudija;

    public StudentNaGodini() {
    }

    public StudentNaGodini(Long id, LocalDateTime datumUpisa, String brojIndeksa, Student student) {
        this.id = id;
        this.datumUpisa = datumUpisa;
        this.brojIndeksa = brojIndeksa;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatumUpisa() {
        return datumUpisa;
    }

    public void setDatumUpisa(LocalDateTime datumUpisa) {
        this.datumUpisa = datumUpisa;
    }

    public String getBrojIndeksa() {
        return brojIndeksa;
    }

    public void setBrojIndeksa(String brojIndeksa) {
        this.brojIndeksa = brojIndeksa;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
