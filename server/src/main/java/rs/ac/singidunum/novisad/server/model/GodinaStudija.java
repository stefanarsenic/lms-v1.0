package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.time.Year;
import java.util.Set;

@Entity
public class GodinaStudija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Year godina;
    @OneToMany(mappedBy = "godinaStudija")
    private Set<StudentNaGodini> studenti;
    @OneToMany(mappedBy = "godinaStudija")
    private Set<PlanZaGodinu> planoviZaGodine;
    @ManyToOne
    private StudijskiProgram studijskiProgram;

    public GodinaStudija() {
    }

    public GodinaStudija(Long id, Year godina, Set<StudentNaGodini> studenti, Set<PlanZaGodinu> planoviZaGodine, StudijskiProgram studijskiProgram) {
        this.id = id;
        this.godina = godina;
        this.studenti = studenti;
        this.planoviZaGodine = planoviZaGodine;
        this.studijskiProgram = studijskiProgram;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Year getGodina() {
        return godina;
    }

    public void setGodina(Year godina) {
        this.godina = godina;
    }

    public Set<StudentNaGodini> getStudenti() {
        return studenti;
    }

    public void setStudenti(Set<StudentNaGodini> studenti) {
        this.studenti = studenti;
    }

    public Set<PlanZaGodinu> getPlanoviZaGodine() {
        return planoviZaGodine;
    }

    public void setPlanoviZaGodine(Set<PlanZaGodinu> planoviZaGodine) {
        this.planoviZaGodine = planoviZaGodine;
    }

    public StudijskiProgram getStudijskiProgram() {
        return studijskiProgram;
    }

    public void setStudijskiProgram(StudijskiProgram studijskiProgram) {
        this.studijskiProgram = studijskiProgram;
    }
}
