package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.*;

import java.util.List;

@Repository
public interface EvaluacijaZnanjaRepository extends JpaRepository<EvaluacijaZnanja, Long> {


    EvaluacijaZnanja findEvaluacijaZnanjaByRealizacijaPredmetaAndIspitniRokAndTipEvaluacije(RealizacijaPredmeta realizacijaPredmeta, IspitniRok ispitniRok, TipEvaluacije tipEvaluacije);

    @Query("SELECT ez " +
            "FROM EvaluacijaZnanja ez " +
            "WHERE ez.realizacijaPredmeta.predmet.id = :predmetId")
    List<EvaluacijaZnanja> findEvaluacijaZnanjaByPredmetId(@Param("predmetId") Long predmetId);

    @Query("SELECT ez " +
            "FROM EvaluacijaZnanja ez " +
            "LEFT JOIN RealizacijaPredmeta rp ON ez.realizacijaPredmeta.id = rp.id " +
            "LEFT JOIN Predmet p " +
            "ON rp.predmet.id = p.id " +
            "LEFT JOIN PredmetPlanaZaGodinu ppzg on p.id = ppzg.predmet.id " +
            "LEFT JOIN PlanZaGodinu pzg ON ppzg.planZaGodinu.id = pzg.id " +
            "WHERE pzg.studijskiProgram.id = :studijskiProgramId AND ez.tipEvaluacije.id = :tipEvaluacijeId AND ez.ispitniRok.id = :ispitniRokId")
    List<EvaluacijaZnanja> findAllByStudijskiProgramAndTipEvaluacijeAndIspitniRok(Long studijskiProgramId, Long tipEvaluacijeId, Long ispitniRokId);
}