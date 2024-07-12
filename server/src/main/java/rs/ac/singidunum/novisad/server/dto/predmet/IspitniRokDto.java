package rs.ac.singidunum.novisad.server.dto.predmet;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.student.GodinaStudijaDto;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IspitniRokDto implements Serializable {
    private Long id;
    private String naziv;
    private LocalDate pocetak;
    private LocalDate kraj;
    private GodinaStudijaDto godinaStudija;
}