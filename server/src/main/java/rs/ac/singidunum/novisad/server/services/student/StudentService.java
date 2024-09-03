package rs.ac.singidunum.novisad.server.services.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.dto.student.StudentInfoDTO;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.repositories.student.StudentRepository;

import java.util.List;

@Service
public class StudentService extends GenericService<Student, Long> {
    private final StudentRepository studentRepository;
    public StudentService(CrudRepository<Student, Long> repository, StudentRepository studentRepository) {
        super(repository);
        this.studentRepository = studentRepository;
    }

    public String findNazivDrzaveByStudentId(Long studentId){
        return studentRepository.findNazivDrzaveByStudentId(studentId);
    }

    public List<Object[]> getStudentInfo(Long studentId) {
        return studentRepository.getStudentInfo(studentId);
    }
}
