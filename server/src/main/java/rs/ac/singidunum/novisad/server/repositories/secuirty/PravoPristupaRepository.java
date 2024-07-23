package rs.ac.singidunum.novisad.server.repositories.secuirty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa;

@Repository
public interface PravoPristupaRepository extends JpaRepository<PravoPristupa,Long> {
}
