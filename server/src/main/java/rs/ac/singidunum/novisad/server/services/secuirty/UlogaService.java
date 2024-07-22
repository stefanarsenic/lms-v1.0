package rs.ac.singidunum.novisad.server.services.secuirty;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.Uloga;

@Service
public class UlogaService extends GenericService<Uloga,Long> {
    public UlogaService(CrudRepository<Uloga, Long> repository) {
        super(repository);
    }
}
