package rs.ac.singidunum.novisad.server.repositories.predmet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.predmet.Ispit;
@Repository
public interface IspitRepository extends JpaRepository<Ispit, Long> {
}