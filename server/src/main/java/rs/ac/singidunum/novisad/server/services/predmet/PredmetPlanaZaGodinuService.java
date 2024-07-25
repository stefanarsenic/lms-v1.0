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

    @Transactional
    public void createWithPredmeti(List<Predmet> predmeti, Long studijskiProgramId, Integer godina){
        StudijskiProgram studijskiProgram = studijskiProgramRepository.findById(studijskiProgramId).orElseThrow();
        PlanZaGodinu planZaGodinu = planZaGodinuRepository.findByStudijskiProgramAndGodina(studijskiProgram, godina);
        List<PlanZaGodinu> planoviZaGodine = planZaGodinuRepository.findPlanZaGodinusByStudijskiProgram(studijskiProgram);

        List<PredmetPlanaZaGodinu> predmetiPlanaZaGodinu = new ArrayList<>(Collections.emptySet());
        predmeti.forEach(predmet -> {
            List<PredmetPlanaZaGodinu> listaPpzg = new ArrayList<>(Collections.emptySet());

            planoviZaGodine.forEach(pzg -> {
                PredmetPlanaZaGodinu ppzg = predmetPlanaZaGodinuRepository.findByPlanZaGodinuAndPredmet(pzg, predmet);
                if(ppzg != null) {
                    listaPpzg.add(ppzg);
                }
            });

            if(listaPpzg.isEmpty()) {
                PredmetPlanaZaGodinu p = predmetPlanaZaGodinuRepository.findByPlanZaGodinuAndPredmet(planZaGodinu, predmet);
                if (p != null) {
                    p.setPredmet(predmet);
                    p.setPlanZaGodinu(planZaGodinu);
                    PredmetPlanaZaGodinu saved = predmetPlanaZaGodinuRepository.save(p);
                    predmetiPlanaZaGodinu.add(saved);
                } else {
                    p = new PredmetPlanaZaGodinu();
                    p.setPredmet(predmet);
                    p.setPlanZaGodinu(planZaGodinu);
                    PredmetPlanaZaGodinu saved = predmetPlanaZaGodinuRepository.save(p);
                    predmetiPlanaZaGodinu.add(saved);
                }
            }
        });
    }
}
