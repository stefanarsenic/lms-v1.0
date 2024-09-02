package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.RealizacijaPredmetaRepository;

import java.util.Optional;

@Service
public class RealizacijaPredmetaService extends GenericService<RealizacijaPredmeta, Long> {

    private final RealizacijaPredmetaRepository realizacijaPredmetaRepository;
    private final PredmetRepository predmetRepository;
    public RealizacijaPredmetaService(CrudRepository<RealizacijaPredmeta, Long> repository, RealizacijaPredmetaRepository realizacijaPredmetaRepository, PredmetRepository predmetRepository) {
        super(repository);
        this.realizacijaPredmetaRepository = realizacijaPredmetaRepository;
        this.predmetRepository = predmetRepository;
    }

    @Transactional
    public RealizacijaPredmeta findByPredmetId(Long predmetId){
        Optional<RealizacijaPredmeta> realizacijaPredmeta = Optional.ofNullable(realizacijaPredmetaRepository.findRealizacijaPredmetaByPredmetId(predmetId));
        if(realizacijaPredmeta.isEmpty()){
            RealizacijaPredmeta rp = new RealizacijaPredmeta();
            rp.setPredmet(predmetRepository.findById(predmetId).orElseThrow());
            realizacijaPredmetaRepository.save(rp);
            return rp;
        }
        else {
            RealizacijaPredmeta newRp = new RealizacijaPredmeta(
                    realizacijaPredmeta.get().getId(),
                    realizacijaPredmeta.get().getPredmet(),
                    realizacijaPredmeta.get().getTerminiNastave(),
                    realizacijaPredmeta.get().getEvaluacijeZnanja(),
                    realizacijaPredmeta.get().getObavestenja()
            );

            return newRp;
        }
    }
}
