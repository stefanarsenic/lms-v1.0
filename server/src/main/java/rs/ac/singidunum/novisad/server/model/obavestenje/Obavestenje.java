package rs.ac.singidunum.novisad.server.model.obavestenje;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Obavestenje {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime vremePostavljanja;
    private String sadrzaj;
    private String naslov;
    @OneToMany(mappedBy = "obavestenje")
    private Set<Fajl> prilozi;
}
