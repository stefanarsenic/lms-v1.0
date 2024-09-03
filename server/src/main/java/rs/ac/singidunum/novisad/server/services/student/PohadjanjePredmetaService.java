package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;

@Service
public class PohadjanjePredmetaService extends GenericService<PohadjanjePredmeta, Long> {

    public PohadjanjePredmetaService(CrudRepository<PohadjanjePredmeta, Long> repository) {
        super(repository);
    }
}
