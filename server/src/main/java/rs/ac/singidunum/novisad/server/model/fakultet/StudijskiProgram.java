package rs.ac.singidunum.novisad.server.model.fakultet;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.fakultet.Fakultet;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.student.GodinaStudija;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudijskiProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;
    @Lob
    private String opis;
    @ManyToOne
    private Fakultet fakultet;
    @OneToOne
    private Nastavnik rukovodilac;
    @OneToMany(mappedBy = "studijskiProgram") //TODO:Pitati asistenra za ovo
    private Set<GodinaStudija> godineStudija;
}
