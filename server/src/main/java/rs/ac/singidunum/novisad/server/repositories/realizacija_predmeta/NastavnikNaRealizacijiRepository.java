package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.NastavnikNaRealizaciji;

import java.util.Optional;

@Repository
public interface NastavnikNaRealizacijiRepository extends JpaRepository<NastavnikNaRealizaciji, Long> {

    Optional<NastavnikNaRealizaciji> findByNastavnikId(Long nastavnikId);
}