package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

@Entity
public class AngazovanjeNastavnika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer brojCasova;
    @OneToOne
    private Nastavnik nastavnik;
    @OneToOne
    private Predmet predmet;
    @OneToOne
    private TipNastave tipNastave;
}
