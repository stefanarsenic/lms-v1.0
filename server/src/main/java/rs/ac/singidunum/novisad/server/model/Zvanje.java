package rs.ac.singidunum.novisad.server.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Zvanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datumIzbora;
    private LocalDateTime datumPrestanka;

    @ManyToOne
    private Nastavnik nastavnik;
}
