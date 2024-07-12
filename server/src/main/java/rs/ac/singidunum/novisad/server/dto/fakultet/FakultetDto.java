package rs.ac.singidunum.novisad.server.dto.fakultet;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FakultetDto implements Serializable {
    private Long id;
    private String naziv;
    private AdresaDto adresa;
    private UniverzitetDto univerzitet;
    private NastavnikDto nastavnik;
    private Set<StudijskiProgramDto> studijskiProgrami;
}