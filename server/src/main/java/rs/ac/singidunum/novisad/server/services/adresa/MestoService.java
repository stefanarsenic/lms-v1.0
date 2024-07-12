package rs.ac.singidunum.novisad.server.services.adresa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Mesto;

@Service
public class MestoService extends GenericService<Mesto, Long> {
    public MestoService(CrudRepository<Mesto, Long> repository) {
        super(repository);
    }
}
