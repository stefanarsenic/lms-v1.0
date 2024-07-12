package rs.ac.singidunum.novisad.server.model.predmet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToOne
    private Nastavnik nastavnik;
    @OneToOne
    private Nastavnik asistent;
    @OneToMany(mappedBy = "predmet")
    private Set<Ishod> silabus;
    @OneToMany(mappedBy = "predmet", cascade = CascadeType.ALL)
    private Set<Predmet> preduslov;
    @OneToMany(mappedBy = "predmet")
    private Set<PredmetPlanaZaGodinu> planovi;
    @ManyToOne
    private Predmet predmet;
}
