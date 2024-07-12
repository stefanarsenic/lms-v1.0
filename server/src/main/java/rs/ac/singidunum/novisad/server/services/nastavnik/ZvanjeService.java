package rs.ac.singidunum.novisad.server.services.nastavnik;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.Zvanje;

@Service
public class ZvanjeService extends GenericService<Zvanje, Long> {
    public ZvanjeService(CrudRepository<Zvanje, Long> repository) {
        super(repository);
    }
}
