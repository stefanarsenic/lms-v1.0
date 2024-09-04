package rs.ac.singidunum.novisad.server.model.studetnskaSluzba;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.korisnik.Administrator;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ZahtevMaterijala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private RegistrovaniKorisnik korisnik;

    private String naslov;

    private String opis;
    private Timestamp datumPodnosenja;

    private String status;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Administrator admin;

    private Timestamp datumIzmena;

}
