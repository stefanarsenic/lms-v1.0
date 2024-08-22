package rs.ac.singidunum.novisad.server.services.predmet;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.predmet.PredmetPlanaZaGodinu;
import rs.ac.singidunum.novisad.server.repositories.fakultet.StudijskiProgramRepository;
import rs.ac.singidunum.novisad.server.repositories.predmet.PlanZaGodinuRepository;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetPlanaZaGodinuRepository;

import java.util.*;

@Service
public class PredmetPlanaZaGodinuService extends GenericService<PredmetPlanaZaGodinu, Long> {
    private final PredmetPlanaZaGodinuRepository predmetPlanaZaGodinuRepository;
    private final PlanZaGodinuRepository planZaGodinuRepository;
    private final StudijskiProgramRepository studijskiProgramRepository;
    public PredmetPlanaZaGodinuService(CrudRepository<PredmetPlanaZaGodinu, Long> repository, PredmetPlanaZaGodinuRepository predmetPlanaZaGodinuRepository, PlanZaGodinuRepository planZaGodinuRepository, StudijskiProgramRepository studijskiProgramRepository) {
        super(repository);
        this.predmetPlanaZaGodinuRepository = predmetPlanaZaGodinuRepository;
        this.planZaGodinuRepository = planZaGodinuRepository;
        this.studijskiProgramRepository = studijskiProgramRepository;
    }

    public PredmetPlanaZaGodinu findByPlanZaGodinuAndPredmet(PlanZaGodinu planZaGodinu, Predmet predmet){
        return predmetPlanaZaGodinuRepository.findByPlanZaGodinuAndPredmet(planZaGodinu, predmet);

    }

    public List<PredmetPlanaZaGodinu> findPredmetPlanaZaGodinusByPlanZaGodinu(PlanZaGodinu planZaGodinu){
        return predmetPlanaZaGodinuRepository.findPredmetPlanaZaGodinusByPlanZaGodinu(planZaGodinu);
    }

}
