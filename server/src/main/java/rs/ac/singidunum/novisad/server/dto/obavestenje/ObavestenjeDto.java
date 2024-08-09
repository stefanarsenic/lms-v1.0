package rs.ac.singidunum.novisad.server.dto.obavestenje;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.RealizacijaPredmetaDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObavestenjeDto implements Serializable {
    private Long id;
    private LocalDateTime vremePostavljanja;
    private String sadrzaj;
    private String naslov;
    private Set<FajlDto> prilozi;
    private RealizacijaPredmetaDto realizacijaPredmeta;
}