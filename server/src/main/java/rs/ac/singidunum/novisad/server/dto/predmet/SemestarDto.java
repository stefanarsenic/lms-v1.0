package rs.ac.singidunum.novisad.server.dto.predmet;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.student.SkolskaGodina;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemestarDto implements Serializable {
    private Long id;
    private Integer redniBrojSemestra;
    private LocalDateTime pocetakSemestra;
    private LocalDateTime krajSemestra;
    private SkolskaGodina skolskaGodina;
}
