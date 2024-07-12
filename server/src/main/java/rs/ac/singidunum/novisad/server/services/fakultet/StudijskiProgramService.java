package rs.ac.singidunum.novisad.server.services.fakultet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;

@Service
public class StudijskiProgramService extends GenericService<StudijskiProgram, Long> {
    public StudijskiProgramService(CrudRepository<StudijskiProgram, Long> repository) {
        super(repository);
    }
}
