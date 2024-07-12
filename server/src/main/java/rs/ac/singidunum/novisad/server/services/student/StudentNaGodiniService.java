package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

@Service
public class StudentNaGodiniService extends GenericService<StudentNaGodini, Long> {
    public StudentNaGodiniService(CrudRepository<StudentNaGodini, Long> repository) {
        super(repository);
    }
}
