package rs.ac.singidunum.novisad.server.repositories.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.student.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT d.naziv " +
            "FROM Student s " +
            "LEFT JOIN s.adresa a " +
            "LEFT JOIN a.mesto m " +
            "LEFT JOIN m.drzava d " +
            "WHERE s.id = :studentId")
    String findNazivDrzaveByStudentId(@Param("studentId") Long studentId);
}