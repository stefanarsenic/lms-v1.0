package rs.ac.singidunum.novisad.server.repositories.korisnik;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<RegistrovaniKorisnik,Long> {
     RegistrovaniKorisnik findByKorisnickoIme(String korisnickoIme);
     RegistrovaniKorisnik findByEmail(String email);


}
