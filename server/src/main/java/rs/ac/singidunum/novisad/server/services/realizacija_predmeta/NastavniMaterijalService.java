package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.NastavniMaterijal;

@Service
public class NastavniMaterijalService extends GenericService<NastavniMaterijal, Long> {
    public NastavniMaterijalService(CrudRepository<NastavniMaterijal, Long> repository) {
        super(repository);
    }
}
