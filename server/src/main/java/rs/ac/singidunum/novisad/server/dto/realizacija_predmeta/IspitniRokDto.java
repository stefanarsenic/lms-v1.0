package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import lombok.*;

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
}