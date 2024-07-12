package rs.ac.singidunum.novisad.server.dto.adresa;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrzavaDto implements Serializable {
    private Long id;
    private String naziv;
    private Set<MestoDto> mesta;
}