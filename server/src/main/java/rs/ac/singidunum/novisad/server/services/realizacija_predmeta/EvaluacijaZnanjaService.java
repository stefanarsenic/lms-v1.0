package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.IspitniRok;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipEvaluacije;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.EvaluacijaZnanjaRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.TipEvaluacijeRepository;

import java.util.List;

@Service
public class EvaluacijaZnanjaService extends GenericService<EvaluacijaZnanja, Long> {

    private final EvaluacijaZnanjaRepository evaluacijaZnanjaRepository;
    private final TipEvaluacijeRepository tipEvaluacijeRepository;

    public EvaluacijaZnanjaService(CrudRepository<EvaluacijaZnanja, Long> repository, EvaluacijaZnanjaRepository evaluacijaZnanjaRepository, TipEvaluacijeRepository tipEvaluacijeRepository) {
        super(repository);
        this.evaluacijaZnanjaRepository = evaluacijaZnanjaRepository;
        this.tipEvaluacijeRepository = tipEvaluacijeRepository;
    }

    public EvaluacijaZnanja findIspitByRealizacijaPredmetaAndIspitniRok(RealizacijaPredmeta realizacijaPredmeta, IspitniRok ispitniRok){
        TipEvaluacije tipEvaluacije = tipEvaluacijeRepository.findByNaziv("Usmeni Ispit");
        return evaluacijaZnanjaRepository.findEvaluacijaZnanjaByRealizacijaPredmetaAndIspitniRokAndTipEvaluacije(realizacijaPredmeta, ispitniRok, tipEvaluacije);
    }
    public List<EvaluacijaZnanja> findAllByStudijskiProgramAndTipEvaluacijeAndIspitniRok(Long studijskiProgramId, Long ispitniRokId){
        TipEvaluacije tipEvaluacije = tipEvaluacijeRepository.findByNaziv("Usmeni Ispit");
        return evaluacijaZnanjaRepository.findAllByStudijskiProgramAndTipEvaluacijeAndIspitniRok(studijskiProgramId, tipEvaluacije.getId(), ispitniRokId);
    }
    public List<EvaluacijaZnanja> findEvaluacijaZnanjaByPredmetId(Long predmetId){
        return evaluacijaZnanjaRepository.findEvaluacijaZnanjaByPredmetId(predmetId);
    }
}
