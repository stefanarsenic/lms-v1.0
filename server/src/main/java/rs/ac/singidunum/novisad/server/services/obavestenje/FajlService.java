package rs.ac.singidunum.novisad.server.services.obavestenje;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;

@Service
public class FajlService extends GenericService<Fajl, Long> {
    public FajlService(CrudRepository<Fajl, Long> repository) {
        super(repository);
    }
}
