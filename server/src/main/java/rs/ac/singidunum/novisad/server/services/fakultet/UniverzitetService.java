package rs.ac.singidunum.novisad.server.services.fakultet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.Univerzitet;

@Service
public class UniverzitetService extends GenericService<Univerzitet, Long> {
    public UniverzitetService(CrudRepository<Univerzitet, Long> repository) {
        super(repository);
    }
}
