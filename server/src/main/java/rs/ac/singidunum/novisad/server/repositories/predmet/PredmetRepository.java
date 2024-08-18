package rs.ac.singidunum.novisad.server.repositories.predmet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.Student;

import java.util.List;

@Repository
public interface PredmetRepository extends JpaRepository<Predmet, Long> {

    @Query("select p from Predmet p " +
            "left join PredmetPlanaZaGodinu ppzg " +
            "on p.id = ppzg.predmet.id " +
            "left join PlanZaGodinu pzg " +
            "on ppzg.planZaGodinu.id = pzg.id " +
            "left join Ispit i " +
            "on p.id = i.predmet.id " +
            "where pzg.studijskiProgram.id = :studijskiProgramId " +
            "and i.predmet.id is null")
    List<Predmet> findPredmetiNotExistingInIspitByStudijskiProgram(Long studijskiProgramId);

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

    @Query("SELECT p.espb from Predmet p " +
            "WHERE p.id = :predmetId ")
    Integer getEspbOfPredmet(@Param("predmetId") Long predmetId);

    List<Predmet> findByNastavnik(Nastavnik nastavnik);

    @Query("SELECT s FROM Student s " +
            "JOIN StudentNaGodini sng ON s.id = sng.student.id " +
            "JOIN StudijskiProgram sp ON sng.studijskiProgram.id = sp.id " +
            "JOIN PlanZaGodinu pzg ON sp.id = pzg.studijskiProgram.id " +
            "JOIN PredmetPlanaZaGodinu ppg ON pzg.id = ppg.planZaGodinu.id " +
            "JOIN Predmet p ON ppg.predmet.id = p.id " +
            "WHERE p.id = :predmetId")
    List<Student> findStudentsByPredmetId(@Param("predmetId") Long predmetId);
}