package rs.ac.singidunum.novisad.server.model.predmet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.student.GodinaStudija;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlanZaGodinu {

    @Id
    @GeneratedValue
    private Long id;
    private Integer godina;
    @ManyToOne
    private GodinaStudija godinaStudija;
    @OneToMany(mappedBy = "planZaGodinu")
    private Set<PredmetPlanaZaGodinu> predmetiPlanaZaGodinu;

}
