package rs.ac.singidunum.novisad.server.services.secuirty;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.Uloga;
import rs.ac.singidunum.novisad.server.repositories.korisnik.KorisnikRepository;
import rs.ac.singidunum.novisad.server.repositories.secuirty.UlogaRepository;

import java.util.List;

@Service
public class UlogaService extends GenericService<Uloga,Long> {

    UlogaRepository ulogaRepository;
    public UlogaService(UlogaRepository repository) {
        super(repository);
        ulogaRepository=repository;
    }

    @Transactional
    public void deleteRoles(List<Long> userIds) {
        ulogaRepository.deleteByIdIn(userIds);
    }
}
