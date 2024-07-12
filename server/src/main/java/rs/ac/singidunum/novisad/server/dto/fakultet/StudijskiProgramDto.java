package rs.ac.singidunum.novisad.server.dto.fakultet;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.student.GodinaStudijaDto;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudijskiProgramDto implements Serializable {
    private Long id;
    private String naziv;
    private String opis;
    private FakultetDto fakultet;
    private NastavnikDto rukovodilac;
    private Set<GodinaStudijaDto> godineStudija;
}