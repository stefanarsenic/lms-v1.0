package rs.ac.singidunum.novisad.server.services.predmet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;

@Service
public class PlanZaGodinuService extends GenericService<PlanZaGodinu, Long> {
    public PlanZaGodinuService(CrudRepository<PlanZaGodinu, Long> repository) {
        super(repository);
    }
}
