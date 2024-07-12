package rs.ac.singidunum.novisad.server.services.nastavnik;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;

@Service
public class AngazovanjeNastavnikaService extends GenericService<AngazovanjeNastavnika, Long> {
    public AngazovanjeNastavnikaService(CrudRepository<AngazovanjeNastavnika, Long> repository) {
        super(repository);
    }
}
