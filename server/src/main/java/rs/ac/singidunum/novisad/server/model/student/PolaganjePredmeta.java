package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.Student;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PolaganjePredmeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer ocena;
    private Integer bodovi;
    private Integer brojPolaganja;
    @OneToOne
    private Student student;
    @OneToOne
    private Predmet predmet;
}
