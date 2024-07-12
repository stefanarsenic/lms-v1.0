package rs.ac.singidunum.novisad.server.services.obavestenje;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;

@Service
public class ObavestenjeService extends GenericService<Obavestenje, Long> {
    public ObavestenjeService(CrudRepository<Obavestenje, Long> repository) {
        super(repository);
    }
}
