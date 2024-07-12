package rs.ac.singidunum.novisad.server.services.fakultet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.Fakultet;

@Service
public class FakultetService extends GenericService<Fakultet, Long> {
    public FakultetService(CrudRepository<Fakultet, Long> repository) {
        super(repository);
    }
}
