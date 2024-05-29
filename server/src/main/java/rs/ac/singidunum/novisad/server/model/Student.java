package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ime;
    private String jmbg;
    @ManyToOne
    private GodinaStudija godinaStudija;
    public Student() {
    }

    public Student(Long id, String ime, String jmbg) {
        this.id = id;
        this.ime = ime;
        this.jmbg = jmbg;
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

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }
}
