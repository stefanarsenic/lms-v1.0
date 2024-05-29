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

    public PredmetPlanaZaGodinu() {
    }

    public PredmetPlanaZaGodinu(Long id, PlanZaGodinu planZaGodinu, Predmet predmet) {
        this.id = id;
        this.planZaGodinu = planZaGodinu;
        this.predmet = predmet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanZaGodinu getPlanZaGodinu() {
        return planZaGodinu;
    }

    public void setPlanZaGodinu(PlanZaGodinu planZaGodinu) {
        this.planZaGodinu = planZaGodinu;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }
}
