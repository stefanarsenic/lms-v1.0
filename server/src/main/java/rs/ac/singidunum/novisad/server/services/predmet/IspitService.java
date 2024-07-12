package rs.ac.singidunum.novisad.server.services.predmet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Ispit;

@Service
public class IspitService extends GenericService<Ispit, Long> {
    public IspitService(CrudRepository<Ispit, Long> repository) {
        super(repository);
    }
}
