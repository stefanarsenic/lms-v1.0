package rs.ac.singidunum.novisad.server.services.predmet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;

import java.util.List;

@Service
public class PredmetService extends GenericService<Predmet, Long> {

    private final PredmetRepository predmetRepository;
    public PredmetService(CrudRepository<Predmet, Long> repository, PredmetRepository predmetRepository) {
        super(repository);
        this.predmetRepository = predmetRepository;
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
