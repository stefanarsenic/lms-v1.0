package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.IspitniRok;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolaganjePredmeta;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.util.List;
import java.util.Optional;

@Repository
public interface PohadjanjePredmetaRepository extends JpaRepository<PohadjanjePredmeta, Long> {

    PohadjanjePredmeta findByStudentAndPredmetAndIspitniRok(StudentNaGodini student, Predmet predmet, IspitniRok ispitniRok);
    @Query("SELECT pp " +
            "FROM PohadjanjePredmeta pp " +
            "WHERE pp.konacnaOcena IS NOT NULL AND pp.student.id = :studentId")
    List<PohadjanjePredmeta> findPolozeniPredmetiByStudent(Long studentId);
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

    @Query("SELECT SUM(p.bodovi) " +
            "FROM PohadjanjePredmeta pp " +
            "LEFT JOIN PohadjanjePredmetaHasPolaganja pphp " +
            "ON pp.id = pphp.pohadjanjePredmeta.id " +
            "LEFT JOIN Polaganje p " +
            "ON pphp.polaganje.id = p.id " +
            "WHERE p.student.id = :studentId " +
            "AND pp.predmet.id = :predmetId")
    Integer getBodoviPolaganjaByPredmetAndStudent(@Param("studentId") Long studentId,
                                         @Param("predmetId") Long predmetId);



}
