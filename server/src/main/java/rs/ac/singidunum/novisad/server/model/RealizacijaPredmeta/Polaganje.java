package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Polaganje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer bodovi;
    private String napomena;
    @ManyToOne
    private StudentNaGodini student;
    @ManyToOne
    private EvaluacijaZnanja evaluacijaZnanja;
}
