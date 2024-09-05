package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.IspitniRok;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipEvaluacije;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.EvaluacijaZnanjaRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.TipEvaluacijeRepository;
import rs.ac.singidunum.novisad.server.repositories.student.StudentNaGodiniRepository;

import java.util.List;

@Service
public class EvaluacijaZnanjaService extends GenericService<EvaluacijaZnanja, Long> {

    private final EvaluacijaZnanjaRepository evaluacijaZnanjaRepository;
    private final TipEvaluacijeRepository tipEvaluacijeRepository;
    private final PredmetRepository predmetRepository;
    private final StudentNaGodiniRepository studentNaGodiniRepository;

    public EvaluacijaZnanjaService(CrudRepository<EvaluacijaZnanja, Long> repository, EvaluacijaZnanjaRepository evaluacijaZnanjaRepository, TipEvaluacijeRepository tipEvaluacijeRepository,
                                   PredmetRepository predmetRepository,
                                   StudentNaGodiniRepository studentNaGodiniRepository) {
        super(repository);
        this.evaluacijaZnanjaRepository = evaluacijaZnanjaRepository;
        this.tipEvaluacijeRepository = tipEvaluacijeRepository;
        this.predmetRepository = predmetRepository;
        this.studentNaGodiniRepository = studentNaGodiniRepository;
    }

    public List<EvaluacijaZnanja> findNepolaganeEvaluacijeZnanja(Long predmetId, Long studentId){
        Predmet predmet = predmetRepository.findById(predmetId).orElseThrow();
        StudentNaGodini student = studentNaGodiniRepository.findById(studentId).orElseThrow();
        return evaluacijaZnanjaRepository.findNepolaganeEvaluacijeZnanja(predmetId, 1L, studentId);
    }
    public EvaluacijaZnanja findIspitByRealizacijaPredmetaAndIspitniRok(RealizacijaPredmeta realizacijaPredmeta, IspitniRok ispitniRok){
        TipEvaluacije tipEvaluacije = tipEvaluacijeRepository.findByNaziv("Usmeni Ispit");
        return evaluacijaZnanjaRepository.findEvaluacijaZnanjaByRealizacijaPredmetaAndIspitniRokAndTipEvaluacije(realizacijaPredmeta, ispitniRok, tipEvaluacije).orElseThrow(() -> new EntityNotFoundException("Evaluacija znanja not found"));
    }
    public List<EvaluacijaZnanja> findAllByStudijskiProgramAndTipEvaluacijeAndIspitniRok(Long studijskiProgramId, Long ispitniRokId){
        TipEvaluacije tipEvaluacije = tipEvaluacijeRepository.findByNaziv("Usmeni Ispit");
        return evaluacijaZnanjaRepository.findAllByStudijskiProgramAndTipEvaluacijeAndIspitniRok(studijskiProgramId, tipEvaluacije.getId(), ispitniRokId);
    }
    public List<EvaluacijaZnanja> findEvaluacijaZnanjaByPredmetId(Long predmetId){
        return evaluacijaZnanjaRepository.findEvaluacijaZnanjaByPredmetId(predmetId);
    }
}
