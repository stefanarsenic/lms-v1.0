package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NastavniMaterijalDto implements Serializable {
    private Long id;
    private String naziv;
    private LocalDateTime godinaIzdavanja;
    private Set<NastavnikDto> autori;
    private FajlDto fajl;
}