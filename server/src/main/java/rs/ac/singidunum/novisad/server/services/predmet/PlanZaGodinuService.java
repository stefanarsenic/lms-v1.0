package rs.ac.singidunum.novisad.server.services.predmet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.repositories.predmet.PlanZaGodinuRepository;

import java.util.List;

@Service
public class PlanZaGodinuService extends GenericService<PlanZaGodinu, Long> {
    private final PlanZaGodinuRepository planZaGodinuRepository;
    public PlanZaGodinuService(CrudRepository<PlanZaGodinu, Long> repository, PlanZaGodinuRepository planZaGodinuRepository) {
        super(repository);
        this.planZaGodinuRepository = planZaGodinuRepository;
    }

    public List<PlanZaGodinu> findByStudijskiProgram(StudijskiProgram studijskiProgram){
        return planZaGodinuRepository.findPlanZaGodinusByStudijskiProgram(studijskiProgram);
    }
    public Integer getGodinaByPredmetAndStudijskiProgram(Predmet predmet, StudijskiProgram studijskiProgram){
        return planZaGodinuRepository.getGodinaByPredmetAndStudijskiProgram(predmet.getId(), studijskiProgram.getId());
    }
    public Integer getPotrebnoEspbByStudijskiProgramIdAndGodina(Long studijskiProgramId, Integer godina){
        return planZaGodinuRepository.getPotrebnoEspbByStudijskiProgramIdAndGodina(studijskiProgramId, godina);
    }
    public PlanZaGodinu findByStudijskiProgramAndGodina(StudijskiProgram studijskiProgram, Integer godina){
        return planZaGodinuRepository.findByStudijskiProgramAndGodina(studijskiProgram, godina);
    }
}
