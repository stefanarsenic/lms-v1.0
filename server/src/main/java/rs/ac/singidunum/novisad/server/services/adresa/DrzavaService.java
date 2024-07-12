package rs.ac.singidunum.novisad.server.services.adresa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Drzava;

@Service
public class DrzavaService extends GenericService<Drzava, Long> {
    public DrzavaService(CrudRepository<Drzava, Long> repository) {
        super(repository);
    }
}
