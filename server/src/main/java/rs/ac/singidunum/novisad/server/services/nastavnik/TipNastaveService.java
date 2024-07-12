package rs.ac.singidunum.novisad.server.services.nastavnik;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipNastave;

@Service
public class TipNastaveService extends GenericService<TipNastave, Long> {
    public TipNastaveService(CrudRepository<TipNastave, Long> repository) {
        super(repository);
    }
}
