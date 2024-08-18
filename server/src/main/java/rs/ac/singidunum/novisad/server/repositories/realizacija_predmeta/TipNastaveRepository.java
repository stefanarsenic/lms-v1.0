package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipNastave;
@Repository
public interface TipNastaveRepository extends JpaRepository<TipNastave, Long> {
}