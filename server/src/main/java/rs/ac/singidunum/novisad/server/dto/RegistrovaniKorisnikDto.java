package rs.ac.singidunum.novisad.server.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RegistrovaniKorisnikDto implements Serializable {
    Long id;
    String korisnickoIme;
    String lozinka;
    String email;
    String ime;
    String prezime;
    Set<PravoPristupaDto> pravoPristupaSet;
}