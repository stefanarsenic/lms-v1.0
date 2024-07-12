package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;

@Service
public class EvaluacijaZnanjaService extends GenericService<EvaluacijaZnanja, Long> {
    public EvaluacijaZnanjaService(CrudRepository<EvaluacijaZnanja, Long> repository) {
        super(repository);
    }
}
