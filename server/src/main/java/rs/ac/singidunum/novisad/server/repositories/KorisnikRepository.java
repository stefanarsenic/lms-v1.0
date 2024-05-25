package rs.ac.singidunum.novisad.server.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.Korisnik;

import java.util.Optional;

@Repository
public interface KorisnikRepository extends CrudRepository<Korisnik,Long> {
     Optional<Korisnik> findByKorisnickoIme(String korisnickoIme);
}
