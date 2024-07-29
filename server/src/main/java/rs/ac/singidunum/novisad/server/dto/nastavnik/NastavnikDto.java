package rs.ac.singidunum.novisad.server.dto.nastavnik;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.PravoPristupaDto;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NastavnikDto implements Serializable {
    Long id;
    String korisnickoIme;
    String lozinka;
    String email;
    String ime;
    String prezime;
    Set<PravoPristupaDto> pravoPristupaSet;
    String biografija;
    String jmbg;
    Set<ZvanjeDto> zvanja;
}