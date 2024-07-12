package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.NastavniMaterijal;
@Repository
public interface NastavniMaterijalRepository extends JpaRepository<NastavniMaterijal, Long> {
}