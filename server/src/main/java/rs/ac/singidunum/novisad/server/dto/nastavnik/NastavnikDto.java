package rs.ac.singidunum.novisad.server.dto.nastavnik;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NastavnikDto implements Serializable {
    private String ime;
    private String biografija;
    private Set<ZvanjeDto> zvanja;
}
