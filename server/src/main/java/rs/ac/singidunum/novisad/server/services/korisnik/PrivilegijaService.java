package rs.ac.singidunum.novisad.server.services.korisnik;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.Privilegija;

@Service
public class PrivilegijaService extends GenericService<Privilegija, Long> {
    public PrivilegijaService(CrudRepository<Privilegija, Long> repository) {
        super(repository);
    }
}
