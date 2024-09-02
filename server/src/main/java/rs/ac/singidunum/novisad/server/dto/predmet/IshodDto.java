package rs.ac.singidunum.novisad.server.dto.predmet;

import jakarta.persistence.OneToMany;
import lombok.*;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.NastavniMaterijalDto;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.NastavniMaterijal;
import rs.ac.singidunum.novisad.server.model.predmet.ObrazovniCilj;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IshodDto implements Serializable {
    private Long id;
    private String opis;
    private PredmetDto predmet;
    private NastavniMaterijalDto nastavniMaterijal;
    private List<ObrazovniCiljDto> obrazovniCiljevi;
}