package rs.ac.singidunum.novisad.server.repositories.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.services.student.StudentNaGodiniService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentNaGodiniRepository extends JpaRepository<StudentNaGodini, Long> {

    @Query("SELECT s FROM StudentNaGodini s" +
            " LEFT JOIN PohadjanjePredmeta pp " +
            "ON s.id = pp.student.id " +
            "WHERE pp.predmet.id = :predmetId")
    List<StudentNaGodini> findStudentiNaGodiniByPredmet(Long predmetId);

    @Query("SELECT sng FROM StudentNaGodini sng" +
            " WHERE sng.student.id = :studentId ")
    List<StudentNaGodini> findStudentNaGodinisByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT sng FROM StudentNaGodini sng " +
            "WHERE sng.student.id = :studentId " +
            "AND sng.studijskiProgram.id = :studijskiProgramId")
    List<StudentNaGodini> findStudentNaGodiniByStudijskiProgramIdAndStudentId(@Param("studentId") Long studentId, @Param("studijskiProgramId") Long studijskiProgramId);


    @Query("SELECT sng FROM StudentNaGodini sng WHERE sng.student.korisnickoIme = :username")
    List<StudentNaGodini> predmetiPoUsername(@Param("username") String username);
}
