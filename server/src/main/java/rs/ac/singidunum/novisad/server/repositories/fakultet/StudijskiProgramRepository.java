package rs.ac.singidunum.novisad.server.repositories.fakultet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.util.List;

@Repository
public interface StudijskiProgramRepository extends JpaRepository<StudijskiProgram, Long> {
}