package rs.ac.singidunum.novisad.server.repositories.studentskaSluzba;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.studetnskaSluzba.ZahtevMaterijala;

import java.util.List;

@Repository
public interface ZahtevMaterijalaRepository  extends JpaRepository<ZahtevMaterijala, Long> {

    List<ZahtevMaterijala> findByKorisnikId(Long korisnikId);

    List<ZahtevMaterijala> findByKorisnikKorisnickoIme(String korisnickoIme);
}
