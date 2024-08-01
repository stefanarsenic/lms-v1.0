package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolozeniPredmetDto implements Serializable {
    private Long id;
    private Integer konacnaOcena;
    private StudentNaGodiniDto student;
    private PredmetDto predmet;
}