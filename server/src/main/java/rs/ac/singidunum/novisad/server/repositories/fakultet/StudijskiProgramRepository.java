package rs.ac.singidunum.novisad.server.repositories.fakultet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.util.List;

@Repository
public interface StudijskiProgramRepository extends JpaRepository<StudijskiProgram, Long> {

    @Query("SELECT sp FROM StudijskiProgram sp LEFT JOIN StudentNaGodini sng " +
            "ON sp.id = sng.studijskiProgram.id AND sng.student.id = :studentId " +
            "WHERE sng.studijskiProgram.id IS NULL")
    List<StudijskiProgram> findStudijskiProgramiNotConnectedToStudent(@Param("studentId") Long studentId);

}