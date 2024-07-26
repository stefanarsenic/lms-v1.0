package rs.ac.singidunum.novisad.server.services.fakultet;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.repositories.fakultet.StudijskiProgramRepository;
import rs.ac.singidunum.novisad.server.repositories.predmet.PlanZaGodinuRepository;

import java.util.List;

@Service
public class StudijskiProgramService extends GenericService<StudijskiProgram, Long> {

    private final StudijskiProgramRepository studijskiProgramRepository;
    private final PlanZaGodinuRepository planZaGodinuRepository;
    public StudijskiProgramService(CrudRepository<StudijskiProgram, Long> repository, StudijskiProgramRepository studijskiProgramRepository, PlanZaGodinuRepository planZaGodinuRepository) {
        super(repository);
        this.studijskiProgramRepository = studijskiProgramRepository;
        this.planZaGodinuRepository = planZaGodinuRepository;
    }

    private void createPlanZaGodinu(StudijskiProgram studijskiProgram) {
        int godineTrajanja = studijskiProgram.getGodineTrajanja();
        for (int i = 1; i <= godineTrajanja; i++) {
            PlanZaGodinu planZaGodinu = new PlanZaGodinu();
            planZaGodinu.setGodina(i);
            planZaGodinu.setStudijskiProgram(studijskiProgram);
            planZaGodinuRepository.save(planZaGodinu);
        }
    }
    @Transactional
    public StudijskiProgram createStudijskiProgram(StudijskiProgram studijskiProgram){
        List<PlanZaGodinu> planZaGodinuList = planZaGodinuRepository.findPlanZaGodinusByStudijskiProgram(studijskiProgram);

        if (!planZaGodinuList.isEmpty()) {
            planZaGodinuRepository.deletePlanZaGodinusByStudijskiProgram(studijskiProgram);
        }

        StudijskiProgram savedStudijskiProgram = studijskiProgramRepository.save(studijskiProgram);
        createPlanZaGodinu(savedStudijskiProgram);

        return savedStudijskiProgram;
    }
    @Transactional
    public void delete(StudijskiProgram studijskiProgram){
        planZaGodinuRepository.deletePlanZaGodinusByStudijskiProgram(studijskiProgram);
        studijskiProgramRepository.delete(studijskiProgram);
    }

    @Transactional
    public void deleteStudijskiProgram(Long id){
        StudijskiProgram studijskiProgram = studijskiProgramRepository.findById(id).orElseThrow();
        planZaGodinuRepository.deletePlanZaGodinusByStudijskiProgram(studijskiProgram);
        studijskiProgramRepository.deleteById(id);
    }
}
