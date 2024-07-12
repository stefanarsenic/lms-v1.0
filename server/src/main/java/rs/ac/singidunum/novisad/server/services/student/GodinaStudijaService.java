package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.GodinaStudija;

@Service
public class GodinaStudijaService extends GenericService<GodinaStudija, Long> {
    public GodinaStudijaService(CrudRepository<GodinaStudija, Long> repository) {
        super(repository);
    }
}
