package rs.ac.singidunum.novisad.server.controllers.student;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PolaganjePredmetaDto;
import rs.ac.singidunum.novisad.server.dto.student.PohadjanjePredmetaDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolaganjePredmeta;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.model.student.Upis;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfoDto implements Serializable {
    private StudentNaGodiniDto student;
    private Double prosecnaOcena;
    private Integer ects;
    private List<PohadjanjePredmetaDto> polozeniPredmeti;
    private List<PolaganjePredmetaDto> neuspesnaPolaganja;
    private List<Upis> upisi;
}
