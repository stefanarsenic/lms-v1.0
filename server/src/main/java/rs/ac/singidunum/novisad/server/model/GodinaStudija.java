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
}
