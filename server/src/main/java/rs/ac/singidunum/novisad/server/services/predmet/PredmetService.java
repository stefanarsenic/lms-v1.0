package rs.ac.singidunum.novisad.server.services.predmet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;

@Service
public class PredmetService extends GenericService<Predmet, Long> {
    public PredmetService(CrudRepository<Predmet, Long> repository) {
        super(repository);
    }
}
