package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.util.List;

@Repository
public interface PohadjanjePredmetaRepository extends JpaRepository<PohadjanjePredmeta, Long> {

    List<PohadjanjePredmeta> findPohadjanjePredmetasByStudent(StudentNaGodini student);
}
