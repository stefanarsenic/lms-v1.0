package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;

@Service
public class RealizacijaPredmetaService extends GenericService<RealizacijaPredmeta, Long> {
    public RealizacijaPredmetaService(CrudRepository<RealizacijaPredmeta, Long> repository) {
        super(repository);
    }
}
