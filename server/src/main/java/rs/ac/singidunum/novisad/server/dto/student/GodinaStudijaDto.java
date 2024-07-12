package rs.ac.singidunum.novisad.server.dto.student;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PlanZaGodinuDto;

import java.io.Serializable;
import java.time.Year;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GodinaStudijaDto implements Serializable {
    private Long id;
    private Year godina;
    private Set<StudentNaGodiniDto> studenti;
    private Set<PlanZaGodinuDto> planoviZaGodine;
    private StudijskiProgramDto studijskiProgram;
}