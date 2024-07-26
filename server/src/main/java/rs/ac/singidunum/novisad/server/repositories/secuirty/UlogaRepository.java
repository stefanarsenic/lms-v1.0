package rs.ac.singidunum.novisad.server.repositories.secuirty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.korisnik.Uloga;

@Repository
public interface UlogaRepository extends JpaRepository<Uloga,Long> {
}