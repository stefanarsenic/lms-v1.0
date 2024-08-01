package rs.ac.singidunum.novisad.server.model.predmet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;

import java.util.HashSet;
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
    private Integer espb;
    private Boolean obavezan;
    private Integer brojPredavanja;
    private Integer brojVezbi;
    private Integer drugiObliciNastave;
    private Integer istrazivackiRad;
    private Integer ostaliCasovi;
    @ManyToOne
    private Nastavnik nastavnik;
    @ManyToOne
    private Nastavnik asistent;
    @OneToMany(mappedBy = "predmet")
    private Set<Ishod> silabus;
    @ManyToMany
    @JoinTable(
            name = "preduslov",
            joinColumns = @JoinColumn(name = "predmet_id"),
            inverseJoinColumns = @JoinColumn(name = "preduslov_id")
    )
    private Set<Predmet> preduslov = new HashSet<>();
    @ManyToMany(mappedBy = "preduslov")
    private Set<Predmet> predmeti = new HashSet<>();
}
