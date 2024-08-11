package rs.ac.singidunum.novisad.server.model.obavestenje;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Obavestenje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime vremePostavljanja;
    private String sadrzaj;
    private String naslov;
    @OneToMany(mappedBy = "obavestenje")
    private Set<Fajl> prilozi;
    @ManyToOne
    private RealizacijaPredmeta realizacijaPredmeta;

}
