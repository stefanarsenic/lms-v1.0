package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PolaganjePredmetaDto;
import rs.ac.singidunum.novisad.server.dto.student.PohadjanjePredmetaDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolaganjePredmeta;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfoDto implements Serializable {

    private Long id;
    private List<PohadjanjePredmetaDto> polozeniPredmeti;
    private List<PolaganjePredmetaDto> neuspesnaPolaganja;
    private Double prosecnaOcena;
    private Integer ects;
}
