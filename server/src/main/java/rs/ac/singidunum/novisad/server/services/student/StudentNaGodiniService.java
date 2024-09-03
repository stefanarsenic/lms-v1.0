package rs.ac.singidunum.novisad.server.services.student;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.model.student.Upis;
import rs.ac.singidunum.novisad.server.repositories.predmet.PlanZaGodinuRepository;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PohadjanjePredmetaRepository;
import rs.ac.singidunum.novisad.server.repositories.student.SkolskaGodinaRepository;
import rs.ac.singidunum.novisad.server.repositories.student.StudentNaGodiniRepository;
import rs.ac.singidunum.novisad.server.repositories.student.UpisRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentNaGodiniService extends GenericService<StudentNaGodini, Long> {
    private final StudentNaGodiniRepository studentNaGodiniRepository;
    private final UpisRepository upisRepository;
    private final PohadjanjePredmetaService polozeniPredmetService;
    private final PlanZaGodinuRepository planZaGodinuRepository;
    private final SkolskaGodinaRepository skolskaGodinaRepository;
    private final PredmetRepository predmetRepository;
    private final PohadjanjePredmetaRepository pohadjanjePredmetaRepository;

    public StudentNaGodiniService(CrudRepository<StudentNaGodini, Long> repository, StudentNaGodiniRepository studentNaGodiniRepository, UpisRepository upisRepository, PohadjanjePredmetaService polozeniPredmetService, PlanZaGodinuRepository planZaGodinuRepository, SkolskaGodinaRepository skolskaGodinaRepository, PredmetRepository predmetRepository, PohadjanjePredmetaRepository pohadjanjePredmetaRepository) {
        super(repository);
        this.studentNaGodiniRepository = studentNaGodiniRepository;
        this.upisRepository = upisRepository;
        this.polozeniPredmetService = polozeniPredmetService;
        this.planZaGodinuRepository = planZaGodinuRepository;
        this.skolskaGodinaRepository = skolskaGodinaRepository;
        this.predmetRepository = predmetRepository;
        this.pohadjanjePredmetaRepository = pohadjanjePredmetaRepository;
    }

    public List<StudentNaGodini> findAllByStudentId(Long studentId){
        return studentNaGodiniRepository.findStudentNaGodinisByStudentId(studentId);
    }

    @Transactional
    public StudentNaGodini saveWithUpis(StudentNaGodini entity) {
        Long studijskiProgramId = entity.getStudijskiProgram().getId();
        Long studentId = entity.getStudent().getId();

        List<Predmet> predmeti = predmetRepository.findPredmetiByStudijskiProgram(studijskiProgramId);
        List<PohadjanjePredmeta> pohadjanja = new ArrayList<>();

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

            StudentNaGodini saved = studentNaGodiniRepository.save(entity);
            predmeti.forEach(predmet -> {
                PohadjanjePredmeta p = new PohadjanjePredmeta();
                p.setStudent(saved);
                p.setPredmet(predmet);
                pohadjanja.add(p);
                pohadjanjePredmetaRepository.save(p);
            });
            entity.setPredmeti(pohadjanja);

            return saved;
        }

        throw new IllegalStateException("Student je vec upisan na studijski program.");
    }

    @Transactional
    public StudentNaGodini updateWithUpis(StudentNaGodini entity){
        //Integer ostvareniEspbStudenta = polozeniPredmetService.findOstvareniEspbByStudentId(entity.getId());
        Integer potrebnoEspbZaUpis = planZaGodinuRepository.getPotrebnoEspbByStudijskiProgramIdAndGodina(entity.getStudijskiProgram().getId(), entity.getGodinaStudija() + 1);
        Integer brojUpisa = upisRepository.getBrojUpisaGodineByStudentIdAndGodina(entity.getId(), entity.getGodinaStudija());
        Integer godineTrajanja = entity.getStudijskiProgram().getGodineTrajanja();

        Upis upis = new Upis();
        upis.setDatumUpisa(LocalDateTime.now());
        upis.setStudijskiProgram(entity.getStudijskiProgram().getNaziv());
        upis.setEspbNajava(potrebnoEspbZaUpis);
       // upis.setEspbOsvojeno(ostvareniEspbStudenta);
        upis.setSkolskaGodina(null);
        upis.setKojiPut(brojUpisa + 1);
        upis.setStudent(entity);

        if(entity.getGodinaStudija() + 1 <= godineTrajanja /*ostvareniEspbStudenta >= potrebnoEspbZaUpis*/){
            entity.setGodinaStudija(entity.getGodinaStudija() + 1);
            upis.setKojiPut(1);
        }
        upis.setGodinaStudija(entity.getGodinaStudija());

        upisRepository.save(upis);

        return studentNaGodiniRepository.save(entity);
    }

    public List<StudentNaGodini> findAllByStudentUsername(String username) {
        return studentNaGodiniRepository.predmetiPoUsername(username);
    }


}
