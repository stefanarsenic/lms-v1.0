package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.Aktivnost;

@Service
public class AktivnostService extends GenericService<Aktivnost, Long> {
    public AktivnostService(CrudRepository<Aktivnost, Long> repository) {
        super(repository);
    }
}
