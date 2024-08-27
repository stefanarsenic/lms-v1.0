package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;

import java.util.List;

@Repository
public interface EvaluacijaZnanjaRepository extends JpaRepository<EvaluacijaZnanja, Long> {

    @Query("SELECT ez " +
            "FROM EvaluacijaZnanja ez " +
            "WHERE ez.realizacijaPredmeta.predmet.id = :predmetId")
    List<EvaluacijaZnanja> findEvaluacijaZnanjaByPredmetId(@Param("predmetId") Long predmetId);
}