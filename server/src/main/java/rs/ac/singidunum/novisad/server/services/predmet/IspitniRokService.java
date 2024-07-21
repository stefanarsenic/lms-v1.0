package rs.ac.singidunum.novisad.server.services.predmet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.IspitniRok;

@Service
public class IspitniRokService extends GenericService<IspitniRok, Long> {
    public IspitniRokService(CrudRepository<IspitniRok, Long> repository) {
        super(repository);
    }
}