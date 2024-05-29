package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Predmet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;
    private Integer esbp;
    private Boolean obavezan;
    private Integer brojPredavanja;
    private Integer brojVezbi;
    private Integer drugiObliciNastave;
    private Integer istrazivackiRad;
    private Integer ostaliCasovi;
    @OneToMany(mappedBy = "predmet")
    private Set<Ishod> silabus;
    @OneToMany(mappedBy = "predmet", cascade = CascadeType.ALL)
    private Set<Predmet> preduslov;
    @OneToMany(mappedBy = "predmet")
    private Set<PredmetPlanaZaGodinu> planovi;
    @ManyToOne
    private Predmet predmet;

}
