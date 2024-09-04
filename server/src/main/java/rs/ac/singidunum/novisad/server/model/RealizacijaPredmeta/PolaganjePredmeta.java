package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PolaganjePredmeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer konacnaOcena;
    @ManyToOne
    private StudentNaGodini student;
    @ManyToOne
    private Predmet predmet;
}
