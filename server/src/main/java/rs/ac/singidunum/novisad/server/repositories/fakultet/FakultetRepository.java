package rs.ac.singidunum.novisad.server.repositories.fakultet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.fakultet.Fakultet;
@Repository
public interface FakultetRepository extends JpaRepository<Fakultet, Long> {
}