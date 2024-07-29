package rs.ac.singidunum.novisad.server.dto.nastavnik;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZvanjeDto implements Serializable {
    private Long id;
    private LocalDateTime datumIzbora;
    private LocalDateTime datumPrestanka;
    private NastavnikDto nastavnik;
    private TipZvanjaDto tipZvanja;
    private NaucnaOblastDto naucnaOblast;
}