package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipPolaganjaRepository extends JpaRepository<TipPolaganja, Long> {
}