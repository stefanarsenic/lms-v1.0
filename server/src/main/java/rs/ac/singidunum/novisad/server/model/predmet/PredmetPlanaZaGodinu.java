package rs.ac.singidunum.novisad.server.model.predmet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PredmetPlanaZaGodinu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PlanZaGodinu planZaGodinu;
    @ManyToOne
    private Predmet predmet;
}
