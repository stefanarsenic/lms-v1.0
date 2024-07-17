package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.StudentskaAktivnost;

@Service
public class StudentskaAktivnostService extends GenericService<StudentskaAktivnost, Long> {
    public StudentskaAktivnostService(CrudRepository<StudentskaAktivnost, Long> repository) {
        super(repository);
    }
}
