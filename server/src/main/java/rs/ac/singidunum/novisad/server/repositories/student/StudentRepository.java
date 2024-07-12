package rs.ac.singidunum.novisad.server.repositories.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.student.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}