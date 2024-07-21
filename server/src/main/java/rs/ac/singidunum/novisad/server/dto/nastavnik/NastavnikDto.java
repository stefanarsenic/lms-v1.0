package rs.ac.singidunum.novisad.server.dto.nastavnik;

import jakarta.persistence.OneToMany;
import lombok.*;
import rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NastavnikDto implements Serializable {
    private Long id;
    private String korisnickoIme;
    private String email;
    private String ime;
    private String prezime;
    private String biografija;
    private Set<ZvanjeDto> zvanja;
}
