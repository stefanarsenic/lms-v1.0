package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentNaGodini {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datumUpisa;
    private LocalDateTime datumZavrsetka;
    @Column(unique = true)
    private String brojIndeksa;
    private Integer godinaStudija;
    @ManyToOne
    private Student student;
    @ManyToOne
    private StudijskiProgram studijskiProgram;

    @OneToMany
    Set<Predmet> predmeti;

}
