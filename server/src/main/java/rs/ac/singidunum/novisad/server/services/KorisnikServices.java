package rs.ac.singidunum.novisad.server.services;


import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.repositories.KorisnikRepository;

import java.util.Optional;

@Service
public class KorisnikServices extends GenericService<RegistrovaniKorisnik, Long> {
    private final KorisnikRepository korisnikRepository;

    public KorisnikServices(KorisnikRepository korisnikRepository) {
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
