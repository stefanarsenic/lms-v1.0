package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PohadjanjePredmetaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PohadjanjePredmetaService extends GenericService<PohadjanjePredmeta, Long> {
    private final PohadjanjePredmetaRepository pohadjanjePredmetaRepository;
    public PohadjanjePredmetaService(CrudRepository<PohadjanjePredmeta, Long> repository, PohadjanjePredmetaRepository pohadjanjePredmetaRepository) {
        super(repository);
        this.pohadjanjePredmetaRepository = pohadjanjePredmetaRepository;
    }

    public List<PohadjanjePredmeta> findPolozeniPredmetsByStudentId(Long studentId){
        return pohadjanjePredmetaRepository.findPolozeniPredmetsByStudentId(studentId);
    }
    public Optional<Double> averageKonacnaOcenaByStudentId(Long studentId) {
        return pohadjanjePredmetaRepository.getAverageKonacnaOcenaByStudentId(studentId);
    }

    public Integer findOstvareniEspbByStudentId(Long studentId){
        return pohadjanjePredmetaRepository.getOstvareniEspbByStudentId(studentId).orElse(0);
    }

}
