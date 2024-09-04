package rs.ac.singidunum.novisad.server.services.korisnik;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.Administrator;

@Service
public class AdministratorService extends GenericService<Administrator,Long> {

    public AdministratorService(CrudRepository<Administrator, Long> repository) {
        super(repository);
    }
}
