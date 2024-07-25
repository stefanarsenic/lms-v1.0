package rs.ac.singidunum.novisad.server.repositories.predmet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;

import java.util.List;

@Repository
public interface PredmetRepository extends JpaRepository<Predmet, Long> {

    @Query("SELECT p FROM Predmet p " +
            "LEFT JOIN PredmetPlanaZaGodinu ppzg ON p.id = ppzg.predmet.id " +
            "LEFT JOIN PlanZaGodinu pzg ON ppzg.planZaGodinu.id = pzg.id " +
            "WHERE pzg.studijskiProgram.id = :studijskiProgramId " +
            "AND pzg.godina = :godina")
    List<Predmet> findPredmetiByStudijskiProgramAndGodina(@Param("studijskiProgramId") Long studijskiProgramId, @Param("godina") Integer godina);

    @Query("SELECT p FROM Predmet p " +
            "LEFT JOIN PredmetPlanaZaGodinu ppzg ON p.id = ppzg.predmet.id " +
            "LEFT JOIN PlanZaGodinu pzg ON ppzg.planZaGodinu.id = pzg.id " +
            "WHERE pzg.studijskiProgram.id = :studijskiProgramId ")
    List<Predmet> findPredmetiByStudijskiProgram(@Param("studijskiProgramId") Long studijskiProgramId);
}