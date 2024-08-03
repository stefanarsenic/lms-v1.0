package rs.ac.singidunum.novisad.server.model.predmet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlanZaGodinu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer godina;
    private Integer potrebnoEspb;
    private Integer ukupnoEspb;
    @OneToMany(mappedBy = "planZaGodinu")
    private Set<PredmetPlanaZaGodinu> predmetiPlanaZaGodinu;
    @ManyToOne
    private StudijskiProgram studijskiProgram;
}
