package rs.ac.singidunum.novisad.server.repositories.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.student.Upis;

@Repository
public interface UpisRepository extends JpaRepository<Upis, Long> {

    @Query("SELECT count(u) " +
            "FROM Upis u " +
            "WHERE u.student.id = :studentId " +
            "AND u.godinaStudija = :godinaStudija")
    Integer getBrojUpisaGodineByStudentIdAndGodina(@Param("studentId") Long studentId, @Param("godinaStudija") Integer godinaStudija);
}
