package rs.ac.singidunum.novisad.server.services.studentskaSluzba;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.studetnskaSluzba.ZahtevMaterijala;
import rs.ac.singidunum.novisad.server.repositories.studentskaSluzba.ZahtevMaterijalaRepository;

import java.util.List;


@Service
public class ZahtevMaterijalaService extends GenericService<ZahtevMaterijala,Long> {

    ZahtevMaterijalaRepository zahtevMaterijalaRepository;

    public ZahtevMaterijalaService(ZahtevMaterijalaRepository repository) {
        super(repository);
        zahtevMaterijalaRepository=repository;
    }

    public List<ZahtevMaterijala> findByKorisnikId(Long korisnikId) {
        return zahtevMaterijalaRepository.findByKorisnikId(korisnikId);
    }

    public List<ZahtevMaterijala> findByUsername(String username) {
        return zahtevMaterijalaRepository.findByKorisnikKorisnickoIme(username);
    }
}
