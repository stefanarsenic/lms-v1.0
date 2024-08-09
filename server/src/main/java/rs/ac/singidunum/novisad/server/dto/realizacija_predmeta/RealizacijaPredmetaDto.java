package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.obavestenje.ObavestenjeDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RealizacijaPredmetaDto implements Serializable {
    private Long id;
    private PredmetDto predmet;
    private Set<TerminNastaveDto> terminNastave;
    private Set<EvaluacijaZnanjaDto> evaluacijaZnanja;
    private Set<ObavestenjeDto> obavestenja;
}