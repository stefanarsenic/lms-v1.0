package rs.ac.singidunum.novisad.server.services.predmet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Semestar;

@Service
public class SemestarService extends GenericService<Semestar, Long> {
    public SemestarService(CrudRepository<Semestar, Long> repository) {
        super(repository);
    }
}
