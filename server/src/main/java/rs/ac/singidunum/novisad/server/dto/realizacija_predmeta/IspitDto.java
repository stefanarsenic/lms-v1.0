package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.IspitniRok;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IspitDto {
    private Long id;
    private LocalDateTime pocetakIspita;
    private LocalDateTime krajIspita;
    private IspitniRok ispitniRok;
    private Predmet predmet;
    private StudijskiProgram studijskiProgram;
}
