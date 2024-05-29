package rs.ac.singidunum.novisad.server.services;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.Korisnik;
import rs.ac.singidunum.novisad.server.repositories.KorisnikRepository;

import java.util.Optional;

@Service
public class KorisnikServices extends GenericService<Korisnik, Long> {
    private final KorisnikRepository korisnikRepository;

    public KorisnikServices(KorisnikRepository korisnikRepository) {
        super(korisnikRepository);
        this.korisnikRepository = korisnikRepository;
    }

    public Iterable<Korisnik> findAll() {
        return korisnikRepository.findAll();
    }

    public Optional<Korisnik> findByKorisnickoIme(String korisnickoIme) {
        return korisnikRepository.findByKorisnickoIme(korisnickoIme);
    }
}