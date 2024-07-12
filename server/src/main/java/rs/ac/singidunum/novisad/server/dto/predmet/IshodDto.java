package rs.ac.singidunum.novisad.server.dto.predmet;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IshodDto implements Serializable {
    private Long id;
    private String opis;
    private PredmetDto predmet;
}