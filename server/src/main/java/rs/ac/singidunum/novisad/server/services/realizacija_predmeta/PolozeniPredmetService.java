package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolozeniPredmet;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PolozeniPredmetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PolozeniPredmetService extends GenericService<PolozeniPredmet, Long> {
    private final PolozeniPredmetRepository polozeniPredmetRepository;
    public PolozeniPredmetService(CrudRepository<PolozeniPredmet, Long> repository, PolozeniPredmetRepository polozeniPredmetRepository) {
        super(repository);
        this.polozeniPredmetRepository = polozeniPredmetRepository;
    }

    public List<PolozeniPredmet> findPolozeniPredmetsByStudentId(Long studentId){
        return polozeniPredmetRepository.findPolozeniPredmetsByStudentId(studentId);
    }
    public Optional<Double> averageKonacnaOcenaByStudentId(Long studentId) {
        return polozeniPredmetRepository.getAverageKonacnaOcenaByStudentId(studentId);
    }

    public Integer findOstvareniEspbByStudentId(Long studentId){
        return polozeniPredmetRepository.getOstvareniEspbByStudentId(studentId).orElse(0);
    }
}
