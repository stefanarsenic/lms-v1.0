package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.Polaganje;

@Service
public class PolaganjeService extends GenericService<Polaganje, Long> {
    public PolaganjeService(CrudRepository<Polaganje, Long> repository) {
        super(repository);
    }
}
