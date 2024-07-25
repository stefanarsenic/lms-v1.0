package rs.ac.singidunum.novisad.server.services.fakultet;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.repositories.fakultet.StudijskiProgramRepository;
import rs.ac.singidunum.novisad.server.repositories.predmet.PlanZaGodinuRepository;

@Service
public class StudijskiProgramService extends GenericService<StudijskiProgram, Long> {

    private final StudijskiProgramRepository studijskiProgramRepository;
    private final PlanZaGodinuRepository planZaGodinuRepository;
    public StudijskiProgramService(CrudRepository<StudijskiProgram, Long> repository, StudijskiProgramRepository studijskiProgramRepository, PlanZaGodinuRepository planZaGodinuRepository) {
        super(repository);
        this.studijskiProgramRepository = studijskiProgramRepository;
        this.planZaGodinuRepository = planZaGodinuRepository;
    }

    @Transactional
    public StudijskiProgram createStudijskiProgram(StudijskiProgram studijskiProgram){
        StudijskiProgram savedStudijskiProgram = studijskiProgramRepository.save(studijskiProgram);

        int godineTrajanja = savedStudijskiProgram.getGodineTrajanja();
        for(int i = 1;i <= godineTrajanja;i++){
            PlanZaGodinu planZaGodinu = new PlanZaGodinu();
            planZaGodinu.setGodina(i);
            planZaGodinu.setStudijskiProgram(savedStudijskiProgram);
            planZaGodinuRepository.save(planZaGodinu);
        }

        return savedStudijskiProgram;
    }
}
