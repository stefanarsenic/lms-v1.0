package rs.ac.singidunum.novisad.server.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link rs.ac.singidunum.novisad.server.model.korisnik.Uloga}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UlogaDto implements Serializable {
    Long id;
    String naziv;
}