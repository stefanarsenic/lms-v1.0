package rs.ac.singidunum.novisad.server.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PohadjanjePredmetaDto implements Serializable {
    private Long id;
    private Integer konacnaOcena;
    private PredmetDto predmet;
    private StudentNaGodiniDto student;
}
