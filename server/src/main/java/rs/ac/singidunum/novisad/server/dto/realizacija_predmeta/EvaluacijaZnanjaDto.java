package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacijaZnanjaDto implements Serializable {
    private Long id;
    private LocalDateTime vremePocetka;
    private LocalDateTime vremeZavrsetka;
    private Integer bodovi;
    private IshodDto ishod;
    private FajlDto instrumentEvaluacije;
    private TipEvaluacijeDto tipEvaluacije;
    private RealizacijaPredmetaDto realizacijaPredmeta;
    private IspitniRokDto ispitniRok;
}