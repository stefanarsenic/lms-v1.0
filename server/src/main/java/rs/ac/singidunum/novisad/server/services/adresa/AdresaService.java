package rs.ac.singidunum.novisad.server.services.adresa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;

@Service
public class AdresaService extends GenericService<Adresa, Long> {
    public AdresaService(CrudRepository<Adresa, Long> repository) {
        super(repository);
    }
}
