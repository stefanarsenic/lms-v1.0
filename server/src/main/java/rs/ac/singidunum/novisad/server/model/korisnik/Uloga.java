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
public class Uloga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;
    @ManyToMany(mappedBy = "uloge")
    private Collection<RegistrovaniKorisnik> korisnici;

    @ManyToMany
    @JoinTable(
            name = "privilegije_uloga",
            joinColumns = @JoinColumn(
                    name = "uloga_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilegija_id", referencedColumnName = "id"))
    private Collection<Privilegija> privilegije;
}
