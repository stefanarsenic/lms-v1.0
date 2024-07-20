package rs.ac.singidunum.novisad.server.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PravoPristupaDto implements Serializable {
    Long id;
    UlogaDto uloga;



}