package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolaganjePredmeta;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PolaganjePredmetaHasPolaganjeRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PolaganjePredmetaRepository;

@Service
public class PolaganjePredmetaService extends GenericService<PolaganjePredmeta, Long> {
    private final PolaganjePredmetaRepository polaganjePredmetaRepository;
    private final PolaganjePredmetaHasPolaganjeRepository polaganjePredmetaHasPolaganjeRepository;
    public PolaganjePredmetaService(CrudRepository<PolaganjePredmeta, Long> repository, PolaganjePredmetaRepository polaganjePredmetaRepository, PolaganjePredmetaHasPolaganjeRepository polaganjePredmetaHasPolaganjeRepository) {
        super(repository);
        this.polaganjePredmetaRepository = polaganjePredmetaRepository;
        this.polaganjePredmetaHasPolaganjeRepository = polaganjePredmetaHasPolaganjeRepository;
    }

}
