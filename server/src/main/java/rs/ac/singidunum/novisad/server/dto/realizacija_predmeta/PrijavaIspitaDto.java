package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import jakarta.persistence.ManyToOne;
import lombok.*;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.IspitniRok;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrijavaIspitaDto implements Serializable {
    private Long id;
    private StudentNaGodiniDto student;
    private EvaluacijaZnanjaDto evaluacijaZnanja;
    private IspitniRokDto ispitniRok;
}