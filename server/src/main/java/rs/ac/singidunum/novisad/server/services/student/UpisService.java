package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.Upis;

@Service
public class UpisService extends GenericService<Upis, Long> {
    public UpisService(CrudRepository<Upis, Long> repository) {
        super(repository);
    }
}
