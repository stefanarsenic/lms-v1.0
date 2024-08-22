package rs.ac.singidunum.novisad.server.dto.predmet;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PredmetPlanaZaGodinuDto implements Serializable {
    private Long id;
    private Integer semestar;
    private PlanZaGodinuDto planZaGodinu;
    private PredmetDto predmet;
}