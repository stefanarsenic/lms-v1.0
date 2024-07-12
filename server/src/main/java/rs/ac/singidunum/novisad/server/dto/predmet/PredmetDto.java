package rs.ac.singidunum.novisad.server.dto.predmet;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PredmetDto implements Serializable {
    private Long id;
    private String naziv;
    private Integer esbp;
    private Boolean obavezan;
    private Integer brojPredavanja;
    private Integer brojVezbi;
    private Integer drugiObliciNastave;
    private Integer istrazivackiRad;
    private Integer ostaliCasovi;
    private NastavnikDto nastavnik;
    private NastavnikDto asistent;
    private Set<IshodDto> silabus;
    private Set<PredmetDto> preduslov;
    private Set<PredmetPlanaZaGodinuDto> planovi;
}