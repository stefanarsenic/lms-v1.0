package rs.ac.singidunum.novisad.server.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;

import java.util.Optional;

@Repository
public interface KorisnikRepository extends CrudRepository<RegistrovaniKorisnik,Long> {
     Optional<RegistrovaniKorisnik> findByKorisnickoIme(String korisnickoIme);
}
