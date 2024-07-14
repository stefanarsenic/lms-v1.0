package rs.ac.singidunum.novisad.server.model.korisnik;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class RegistrovaniKorisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String korisnickoIme;
    private String lozinka;
    private String email;
    private String ime;
    private String prezime;
    @OneToMany(mappedBy = "registrovaniKorisnik")
    private Set<PravoPristupa> pravoPristupaSet=new HashSet<>();

}
