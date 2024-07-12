package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipEvaluacije;

@Service
public class TipEvaluacijeService extends GenericService<TipEvaluacije, Long> {
    public TipEvaluacijeService(CrudRepository<TipEvaluacije, Long> repository) {
        super(repository);
    }
}
