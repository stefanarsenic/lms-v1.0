package rs.ac.singidunum.novisad.server.repositories.obavestenje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
@Repository
public interface FajlRepository extends JpaRepository<Fajl, Long> {

    Fajl findBySifra(String sifra);
}