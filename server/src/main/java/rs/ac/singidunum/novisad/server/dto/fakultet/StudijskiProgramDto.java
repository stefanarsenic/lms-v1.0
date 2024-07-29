package rs.ac.singidunum.novisad.server.dto.fakultet;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudijskiProgramDto implements Serializable {
    private Long id;
    private String naziv;
    private Integer godineTrajanja;
    private String opis;
    private FakultetDto fakultet;
    private NastavnikDto rukovodilac;
}