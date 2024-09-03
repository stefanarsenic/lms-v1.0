package rs.ac.singidunum.novisad.server.services.predmet;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.NastavnikNaRealizaciji;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.NastavnikNaRealizacijiRepository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.repositories.fakultet.StudijskiProgramRepository;
import rs.ac.singidunum.novisad.server.repositories.nastavnik.NastavnikRepository;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.RealizacijaPredmetaRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.TipNastaveRepository;
import rs.ac.singidunum.novisad.server.repositories.student.StudentNaGodiniRepository;

import java.util.List;

@Service
public class PredmetService extends GenericService<Predmet, Long> {

    private final PredmetRepository predmetRepository;
    private final StudentNaGodiniRepository studentNaGodiniRepository;
    private final StudijskiProgramRepository studijskiProgramRepository;
    private final RealizacijaPredmetaRepository realizacijaPredmetaRepository;
    private final TipNastaveRepository tipNastaveRepository;
    private final NastavnikNaRealizacijiRepository nastavnikNaRealizacijiRepository;
    private final NastavnikRepository nastavnikRepository;

    public PredmetService(CrudRepository<Predmet, Long> repository, PredmetRepository predmetRepository,
                          StudentNaGodiniRepository studentNaGodiniRepository,
                          StudijskiProgramRepository studijskiProgramRepository,
                          RealizacijaPredmetaRepository realizacijaPredmetaRepository,
                          TipNastaveRepository tipNastaveRepository,
                          NastavnikNaRealizacijiRepository nastavnikNaRealizacijiRepository,
                          NastavnikRepository nastavnikRepository) {
        super(repository);
        this.predmetRepository = predmetRepository;
        this.studentNaGodiniRepository = studentNaGodiniRepository;
        this.studijskiProgramRepository = studijskiProgramRepository;
        this.realizacijaPredmetaRepository = realizacijaPredmetaRepository;
        this.tipNastaveRepository = tipNastaveRepository;
        this.nastavnikNaRealizacijiRepository = nastavnikNaRealizacijiRepository;
        this.nastavnikRepository = nastavnikRepository;
    }

    @Transactional
    public Predmet createPredmet(Predmet predmet, Nastavnik n, Nastavnik a){
        Predmet saved = predmetRepository.save(predmet);
        RealizacijaPredmeta r = new RealizacijaPredmeta();
        r.setPredmet(saved);
        realizacijaPredmetaRepository.save(r);

        NastavnikNaRealizaciji nastavnik = new NastavnikNaRealizaciji();
        nastavnik.setNastavnik(n);
        nastavnik.setBrojCasova(saved.getBrojPredavanja());
        nastavnik.setRealizacijaPredmeta(r);
        nastavnik.setTipNastave(tipNastaveRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Tip nastave not found with id: 1")));
        nastavnikNaRealizacijiRepository.save(nastavnik);

        NastavnikNaRealizaciji asistent = new NastavnikNaRealizaciji();
        asistent.setNastavnik(a);
        asistent.setBrojCasova(saved.getBrojVezbi());
        asistent.setRealizacijaPredmeta(r);
        asistent.setTipNastave(tipNastaveRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("Tip nastave not found with id: 2")));
        nastavnikNaRealizacijiRepository.save(asistent);

        return saved;
    }

    public List<Predmet> findAllNePolozeniPredmetiByStudentAndStudijskiProgram(Long studentId, Long studijskiProgramId){
        StudentNaGodini s = studentNaGodiniRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId.toString()));
        StudijskiProgram sp = studijskiProgramRepository.findById(studijskiProgramId).orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId.toString()));

        return predmetRepository.findAllNePolozeniPredmetiByStudentAndStudijskiProgram(studentId, studijskiProgramId, s.getGodinaStudija());
    }
    public List<Predmet> findPredmetiByStudijskiProgramAndSemestar(Long studijskiProgramId, Integer semestar){
        return predmetRepository.findPredmetiByStudijskiProgramAndSemestar(studijskiProgramId, semestar);
    }
    public List<Predmet> findPredmetiByStudijskiProgramAndGodina(Long studijskiProgramId, Integer godina){
        return predmetRepository.findPredmetiByStudijskiProgramAndGodina(studijskiProgramId, godina);
    }
    public List<Predmet> findPredmetiByStudijskiProgram(Long studijskiProgramId){
        return predmetRepository.findPredmetiByStudijskiProgram(studijskiProgramId);
    }
    public Integer getEspbOfPredmet(Long predmetId){
        return predmetRepository.getEspbOfPredmet(predmetId);
    }

    public List<Predmet> getPredmetByNastavnik(Nastavnik nastavnik) {
        return predmetRepository.findByNastavnik(nastavnik);
    }
    public List<Student> getStudentsByPredmet(Long predmetId) {
        return predmetRepository.findStudentsByPredmetId(predmetId);
    }
}
