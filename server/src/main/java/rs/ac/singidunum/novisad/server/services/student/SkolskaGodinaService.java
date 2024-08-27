package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.SkolskaGodina;

@Service
public class SkolskaGodinaService extends GenericService<SkolskaGodina, Long> {
    public SkolskaGodinaService(CrudRepository<SkolskaGodina, Long> repository) {
        super(repository);
    }
}
