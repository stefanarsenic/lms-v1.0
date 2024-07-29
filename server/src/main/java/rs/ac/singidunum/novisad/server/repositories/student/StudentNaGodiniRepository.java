package rs.ac.singidunum.novisad.server.repositories.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.util.List;

@Repository
public interface StudentNaGodiniRepository extends JpaRepository<StudentNaGodini, Long> {

}