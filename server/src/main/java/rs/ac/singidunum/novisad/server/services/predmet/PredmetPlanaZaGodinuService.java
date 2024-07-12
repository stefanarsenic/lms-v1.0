package rs.ac.singidunum.novisad.server.services.predmet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.PredmetPlanaZaGodinu;

@Service
public class PredmetPlanaZaGodinuService extends GenericService<PredmetPlanaZaGodinu, Long> {
    public PredmetPlanaZaGodinuService(CrudRepository<PredmetPlanaZaGodinu, Long> repository) {
        super(repository);
    }
}
