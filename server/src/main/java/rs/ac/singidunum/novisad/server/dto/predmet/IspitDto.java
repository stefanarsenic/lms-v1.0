package rs.ac.singidunum.novisad.server.dto.predmet;

import lombok.*;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IspitDto implements Serializable {
    private Long id;
    private LocalDateTime datumPolaganja;
    private Time termin;
    private String sala;
    private String poruka;
    private IspitniRokDto ispitniRok;
    private PredmetDto predmet;
}