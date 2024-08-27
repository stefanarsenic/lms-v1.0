package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.EvaluacijaZnanjaRepository;

import java.util.List;

@Service
public class EvaluacijaZnanjaService extends GenericService<EvaluacijaZnanja, Long> {

    private final EvaluacijaZnanjaRepository evaluacijaZnanjaRepository;

    public EvaluacijaZnanjaService(CrudRepository<EvaluacijaZnanja, Long> repository, EvaluacijaZnanjaRepository evaluacijaZnanjaRepository) {
        super(repository);
        this.evaluacijaZnanjaRepository = evaluacijaZnanjaRepository;
    }

    public List<EvaluacijaZnanja> findEvaluacijaZnanjaByPredmetId(Long predmetId){
        return evaluacijaZnanjaRepository.findEvaluacijaZnanjaByPredmetId(predmetId);
    }
}
