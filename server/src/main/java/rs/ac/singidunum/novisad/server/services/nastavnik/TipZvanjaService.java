package rs.ac.singidunum.novisad.server.services.nastavnik;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipZvanja;

@Service
public class TipZvanjaService extends GenericService<TipZvanja, Long> {
    public TipZvanjaService(CrudRepository<TipZvanja, Long> repository) {
        super(repository);
    }
}
