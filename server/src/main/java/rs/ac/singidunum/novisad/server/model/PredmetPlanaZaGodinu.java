package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

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
