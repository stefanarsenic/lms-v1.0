package rs.ac.singidunum.novisad.server.services.secuirty;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa;

@Service
public class PravoPristupaService extends GenericService<PravoPristupa,Long> {
    public PravoPristupaService(CrudRepository<PravoPristupa, Long> repository) {
        super(repository);
    }
}
