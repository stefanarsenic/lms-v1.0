package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;

@Repository
public interface RealizacijaPredmetaRepository extends JpaRepository<RealizacijaPredmeta, Long> {

    RealizacijaPredmeta findRealizacijaPredmetaByPredmetId(Long predmetId);

}