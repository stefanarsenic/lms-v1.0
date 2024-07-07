package rs.ac.singidunum.novisad.server.model.korisnik;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Privilegija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;

    @ManyToMany(mappedBy = "privilegije")
    private Collection<Uloga> uloge;
}
