package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;

import java.time.Year;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
