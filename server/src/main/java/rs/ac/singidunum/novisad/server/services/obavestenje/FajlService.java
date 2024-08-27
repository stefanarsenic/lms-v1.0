package rs.ac.singidunum.novisad.server.services.obavestenje;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.repositories.obavestenje.FajlRepository;

@Service
public class FajlService extends GenericService<Fajl, Long> {

    private final FajlRepository fajlRepository;

    public FajlService(CrudRepository<Fajl, Long> repository, FajlRepository fajlRepository) {
        super(repository);
        this.fajlRepository = fajlRepository;
    }

    public Fajl findBySifra(String sifra){
        return fajlRepository.findBySifra(sifra);
    }
}
