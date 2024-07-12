package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.PrijavaIspita;

@Service
public class PrijavaIspitaService extends GenericService<PrijavaIspita, Long> {
    public PrijavaIspitaService(CrudRepository<PrijavaIspita, Long> repository) {
        super(repository);
    }
}
