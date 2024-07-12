package rs.ac.singidunum.novisad.server.dto.fakultet;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.model.fakultet.Univerzitet;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniverzitetDto implements Serializable {
    private Long id;
    private String naziv;
    private LocalDateTime datumOsnovanja;
    private AdresaDto adresa;
    private Set<FakultetDto> fakulteti;
    private NastavnikDto rektor;
}