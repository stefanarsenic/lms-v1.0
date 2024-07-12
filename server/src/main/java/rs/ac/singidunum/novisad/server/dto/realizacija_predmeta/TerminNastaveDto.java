package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.TipNastaveDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TerminNastaveDto implements Serializable {
    private Long id;
    private LocalDateTime vremePocetka;
    private LocalDateTime vremeZavrsetka;
    private IshodDto ishod;
    private TipNastaveDto tipNastave;
    private NastavniMaterijalDto nastavniMaterijal;
}