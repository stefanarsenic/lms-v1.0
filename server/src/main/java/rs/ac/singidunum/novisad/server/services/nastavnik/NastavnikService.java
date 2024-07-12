package rs.ac.singidunum.novisad.server.services.nastavnik;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;

@Service
public class NastavnikService extends GenericService<Nastavnik, Long> {
    public NastavnikService(CrudRepository<Nastavnik, Long> repository) {
        super(repository);
    }
}
