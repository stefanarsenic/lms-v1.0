package rs.ac.singidunum.novisad.server.model.anketa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Anketa {

    @Id
    @GeneratedValue
    private Long id;
    private String naslov;
    @OneToMany(mappedBy = "anketa")
    private Set<Pitanje> pitanja;

    //TODO: dodati grupe korisnika koji mogu da pristupe anketi
}
