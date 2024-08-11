package rs.ac.singidunum.novisad.server.services.obavestenje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;
import rs.ac.singidunum.novisad.server.repositories.obavestenje.ObavestenjeRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.RealizacijaPredmetaRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ObavestenjeService extends GenericService<Obavestenje, Long> {

    @Autowired
    RealizacijaPredmetaRepository realizacijaPredmeta;

    public ObavestenjeService(ObavestenjeRepository repository) {
        super(repository);
    }


    public Set<Obavestenje> findObavestenjaByPredmetIds(List<Long> predmetIds) {
        List<RealizacijaPredmeta> realizacije = realizacijaPredmeta.findByPredmetIdIn(predmetIds);
        return realizacije.stream()
                .flatMap(r -> r.getObavestenja().stream())
                .collect(Collectors.toSet());
    }
}
