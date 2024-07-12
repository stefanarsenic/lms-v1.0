package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RealizacijaPredmetaDto implements Serializable {
    private Long id;
    private PredmetDto predmet;
    private TerminNastaveDto terminNastave;
    private EvaluacijaZnanjaDto evaluacijaZnanja;
}