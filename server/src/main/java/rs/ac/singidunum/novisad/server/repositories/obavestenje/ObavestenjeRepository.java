package rs.ac.singidunum.novisad.server.repositories.obavestenje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;
@Repository
public interface ObavestenjeRepository extends JpaRepository<Obavestenje, Long> {
}