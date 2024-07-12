package rs.ac.singidunum.novisad.server.repositories.nastavnik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipNastave;
@Repository
public interface TipNastaveRepository extends JpaRepository<TipNastave, Long> {
}