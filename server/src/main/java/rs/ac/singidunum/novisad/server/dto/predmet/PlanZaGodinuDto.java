package rs.ac.singidunum.novisad.server.dto.predmet;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanZaGodinuDto implements Serializable {
    private Long id;
    private Integer godina;
    private Integer brojSemestara;
    private Set<PredmetPlanaZaGodinuDto> predmetiPlanaZaGodinu;
    private StudijskiProgramDto studijskiProgram;
}