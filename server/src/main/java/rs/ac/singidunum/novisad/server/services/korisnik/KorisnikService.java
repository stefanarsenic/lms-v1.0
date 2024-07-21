package rs.ac.singidunum.novisad.server.services.korisnik;


import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.repositories.korisnik.KorisnikRepository;

import java.util.Optional;

@Service
public class KorisnikService extends GenericService<RegistrovaniKorisnik, Long> {
    private final KorisnikRepository korisnikRepository;

    public KorisnikService(KorisnikRepository korisnikRepository) {
        super(korisnikRepository);
        this.korisnikRepository = korisnikRepository;
    }

    public Iterable<RegistrovaniKorisnik> findAll() {
        return korisnikRepository.findAll();
    }

    public Optional<RegistrovaniKorisnik> findByKorisnickoIme(String korisnickoIme) {
        return korisnikRepository.findByKorisnickoIme(korisnickoIme);
    }
}