package rs.ac.singidunum.novisad.server.repositories.nastavnik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.nastavnik.Zvanje;
@Repository
public interface ZvanjeRepository extends JpaRepository<Zvanje, Long> {
}