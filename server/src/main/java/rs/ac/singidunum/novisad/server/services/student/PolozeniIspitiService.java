package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.PolozeniIspiti;

@Service
public class PolozeniIspitiService extends GenericService<PolozeniIspiti, Long> {
    public PolozeniIspitiService(CrudRepository<PolozeniIspiti, Long> repository) {
        super(repository);
    }
}
