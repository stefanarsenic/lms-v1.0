package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipPolaganja;

import java.util.List;

@Repository
public interface TipPolaganjaRepository extends JpaRepository<TipPolaganja, Long> {

    List<TipPolaganja> findAllByNazivContains(String slovo);
}