package rs.ac.singidunum.novisad.server.services.predmet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;

@Service
public class IshodService extends GenericService<Ishod, Long> {
    public IshodService(CrudRepository<Ishod, Long> repository) {
        super(repository);
    }
}
