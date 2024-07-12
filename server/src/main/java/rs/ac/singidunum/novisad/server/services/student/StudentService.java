package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.Student;

@Service
public class StudentService extends GenericService<Student, Long> {
    public StudentService(CrudRepository<Student, Long> repository) {
        super(repository);
    }
}
