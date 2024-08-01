package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolozeniPredmet;

import java.util.List;
import java.util.Optional;


@Repository
public interface PolozeniPredmetRepository extends JpaRepository<rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolozeniPredmet, Long> {

    List<PolozeniPredmet> findPolozeniPredmetsByStudentId(Long studentId);
    @Query("SELECT AVG(pp.konacnaOcena) " +
            "FROM PolozeniPredmet pp " +
            "WHERE pp.student.id = :studentId")
    Optional<Double> findAverageKonacnaOcenaByStudentId(@Param("studentId") Long studentId);
}
