package rs.ac.singidunum.novisad.server.services.student;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.model.student.Upis;
import rs.ac.singidunum.novisad.server.repositories.predmet.PlanZaGodinuRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PolozeniPredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.student.SkolskaGodinaRepository;
import rs.ac.singidunum.novisad.server.repositories.student.StudentNaGodiniRepository;
import rs.ac.singidunum.novisad.server.repositories.student.UpisRepository;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.PolozeniPredmetService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentNaGodiniService extends GenericService<StudentNaGodini, Long> {
    private final StudentNaGodiniRepository studentNaGodiniRepository;
    private final UpisRepository upisRepository;
    private final PolozeniPredmetService polozeniPredmetService;
    private final PlanZaGodinuRepository planZaGodinuRepository;
    private final SkolskaGodinaRepository skolskaGodinaRepository;
    public StudentNaGodiniService(CrudRepository<StudentNaGodini, Long> repository, StudentNaGodiniRepository studentNaGodiniRepository, UpisRepository upisRepository, PolozeniPredmetService polozeniPredmetService, PlanZaGodinuRepository planZaGodinuRepository, SkolskaGodinaRepository skolskaGodinaRepository) {
        super(repository);
        this.studentNaGodiniRepository = studentNaGodiniRepository;
        this.upisRepository = upisRepository;
        this.polozeniPredmetService = polozeniPredmetService;
        this.planZaGodinuRepository = planZaGodinuRepository;
        this.skolskaGodinaRepository = skolskaGodinaRepository;
    }

    public List<StudentNaGodini> findAllByStudentId(Long studentId){
        return studentNaGodiniRepository.findStudentNaGodinisByStudentId(studentId);
    }

    @Transactional
    public StudentNaGodini saveWithUpis(StudentNaGodini entity) {
        Long studijskiProgramId = entity.getStudijskiProgram().getId();
        Long studentId = entity.getStudent().getId();

        List<StudentNaGodini> existingStudentNaGodiniList = studentNaGodiniRepository.findStudentNaGodiniByStudijskiProgramIdAndStudentId(studentId, studijskiProgramId);

        if (existingStudentNaGodiniList.isEmpty()) {
            Upis upis = new Upis();
            upis.setDatumUpisa(entity.getDatumUpisa());
            upis.setStudijskiProgram(entity.getStudijskiProgram().getNaziv());
            upis.setGodinaStudija(entity.getGodinaStudija());
            upis.setSkolskaGodina(null);
            upis.setEspbOsvojeno(0);
            upis.setKojiPut(1);
            upis.setStudent(entity);
            upis.setEspbNajava(null);

            upisRepository.save(upis);

            return studentNaGodiniRepository.save(entity);
        }

        throw new IllegalStateException("Student je vec upisan na studijski program.");
    }

    @Transactional
    public StudentNaGodini updateWithUpis(StudentNaGodini entity){
        Integer ostvareniEspbStudenta = polozeniPredmetService.findOstvareniEspbByStudentId(entity.getId());
        Integer potrebnoEspbZaUpis = planZaGodinuRepository.getPotrebnoEspbByStudijskiProgramIdAndGodina(entity.getStudijskiProgram().getId(), entity.getGodinaStudija() + 1);
        Integer brojUpisa = upisRepository.getBrojUpisaGodineByStudentIdAndGodina(entity.getId(), entity.getGodinaStudija());
        Integer godineTrajanja = entity.getStudijskiProgram().getGodineTrajanja();

        Upis upis = new Upis();
        upis.setDatumUpisa(LocalDateTime.now());
        upis.setStudijskiProgram(entity.getStudijskiProgram().getNaziv());
        upis.setEspbNajava(potrebnoEspbZaUpis);
        upis.setEspbOsvojeno(ostvareniEspbStudenta);
        upis.setSkolskaGodina(null);
        upis.setKojiPut(brojUpisa + 1);
        upis.setStudent(entity);

        if(entity.getGodinaStudija() + 1 <= godineTrajanja && ostvareniEspbStudenta >= potrebnoEspbZaUpis){
            entity.setGodinaStudija(entity.getGodinaStudija() + 1);
            upis.setKojiPut(1);
        }
        upis.setGodinaStudija(entity.getGodinaStudija());

        upisRepository.save(upis);

        return studentNaGodiniRepository.save(entity);
    }

//TODO: setovati ostale atribute za upis - treba izdobavljati espb bodove
//TODO: datum zavrsetka
}
