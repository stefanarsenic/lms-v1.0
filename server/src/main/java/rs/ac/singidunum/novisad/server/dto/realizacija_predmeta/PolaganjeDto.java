package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.dto.student.TipPolaganjaDto;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolaganjeDto implements Serializable {

    private Long id;
    private Integer bodovi;
    private String napomena;
    private StudentNaGodiniDto student;
    private EvaluacijaZnanjaDto evaluacijaZnanja;
    private TipPolaganjaDto tipPolaganja;
}
