package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.StudenskaAktivnost;

@Service
public class StudentskaAktivnostService extends GenericService<StudenskaAktivnost, Long> {
    public StudentskaAktivnostService(CrudRepository<StudenskaAktivnost, Long> repository) {
        super(repository);
    }
}
