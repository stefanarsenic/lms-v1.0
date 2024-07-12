package rs.ac.singidunum.novisad.server.services.nastavnik;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.NaucnaOblast;

@Service
public class NaucnaOblastService extends GenericService<NaucnaOblast, Long> {
    public NaucnaOblastService(CrudRepository<NaucnaOblast, Long> repository) {
        super(repository);
    }
}
