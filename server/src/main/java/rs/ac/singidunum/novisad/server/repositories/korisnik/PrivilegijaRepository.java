package rs.ac.singidunum.novisad.server.repositories.korisnik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.korisnik.Privilegija;
@Repository
public interface PrivilegijaRepository extends JpaRepository<Privilegija, Long> {
}