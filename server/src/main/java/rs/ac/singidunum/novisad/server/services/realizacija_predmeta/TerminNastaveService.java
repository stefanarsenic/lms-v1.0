package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;

@Service
public class TerminNastaveService extends GenericService<TerminNastave, Long> {
    public TerminNastaveService(CrudRepository<TerminNastave, Long> repository) {
        super(repository);
    }
}
