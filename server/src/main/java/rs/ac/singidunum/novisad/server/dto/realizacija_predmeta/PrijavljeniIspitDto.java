package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrijavljeniIspitDto implements Serializable {

    private Long id;
    private Integer bodovi;
    private String napomena;
    private PredmetDto predmet;
    private EvaluacijaZnanjaDto evaluacijaZnanja;
}
