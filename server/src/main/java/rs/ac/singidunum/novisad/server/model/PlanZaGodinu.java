package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.util.Set;

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
