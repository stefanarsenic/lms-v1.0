package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.TerminNastaveRepository;

import java.util.List;

@Service
public class TerminNastaveService extends GenericService<TerminNastave, Long> {
    private final TerminNastaveRepository terminNastaveRepository;
    public TerminNastaveService(CrudRepository<TerminNastave, Long> repository, TerminNastaveRepository terminNastaveRepository) {
        super(repository);
        this.terminNastaveRepository = terminNastaveRepository;
    }

    public List<TerminNastave> findByPredmet(Long predmetId){
        return terminNastaveRepository.findTerminiNastaveByPredmetId(predmetId);
    }
}
