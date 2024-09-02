package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;

import java.util.List;
import java.util.Optional;


@Repository
public interface PolozeniPredmetRepository extends JpaRepository<PohadjanjePredmeta, Long> {

    List<PohadjanjePredmeta> findPolozeniPredmetsByStudentId(Long studentId);
    @Query("SELECT AVG(pp.konacnaOcena) " +
            "FROM PohadjanjePredmeta pp " +
            "WHERE pp.student.id = :studentId")
    Optional<Double> getAverageKonacnaOcenaByStudentId(@Param("studentId") Long studentId);
    @Query("SELECT SUM(p.espb) " +
            "FROM Predmet p " +
            "LEFT JOIN PohadjanjePredmeta pp " +
            "ON p.id = pp.predmet.id " +
            "WHERE pp.student.id = :studentId")
    Optional<Integer> getOstvareniEspbByStudentId(@Param("studentId") Long studentId);
}
