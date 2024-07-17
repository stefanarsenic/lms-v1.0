package rs.ac.singidunum.novisad.server.controllers.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.student.GodinaStudijaDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.GodinaStudija;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.services.student.GodinaStudijaService;
import rs.ac.singidunum.novisad.server.services.student.StudentService;

@RestController
@RequestMapping("/api/student-na-godini")
public class StudentNaGodiniController extends GenericController<StudentNaGodini, Long, StudentNaGodiniDto> {

    private final StudentService studentService;
    private final GodinaStudijaService godinaStudijaService;
    public StudentNaGodiniController(GenericService<StudentNaGodini, Long> service, StudentService studentService, GodinaStudijaService godinaStudijaService) {
        super(service);
        this.studentService = studentService;
        this.godinaStudijaService = godinaStudijaService;
    }

    @Override
    protected StudentNaGodiniDto convertToDto(StudentNaGodini entity) throws IllegalAccessException, InstantiationException {
        StudentNaGodiniDto s = EntityDtoMapper.convertToDto(entity, StudentNaGodiniDto.class);
        StudentDto studentDto = EntityDtoMapper.convertToDto(entity.getStudent(), StudentDto.class);
        GodinaStudijaDto godinaStudijaDto = EntityDtoMapper.convertToDto(entity.getGodinaStudija(), GodinaStudijaDto.class);

        s.setStudent(studentDto);
        s.setGodinaStudija(godinaStudijaDto);

        return s;
    }

    @Override
    protected StudentNaGodini convertToEntity(StudentNaGodiniDto dto) throws IllegalAccessException, InstantiationException {
        Student student = studentService.findById(dto.getStudent().getId()).orElseThrow();
        GodinaStudija godinaStudija = godinaStudijaService.findById(dto.getGodinaStudija().getId()).orElseThrow();

        StudentNaGodini studentNaGodini = EntityDtoMapper.convertToDto(dto, StudentNaGodini.class);
        studentNaGodini.setStudent(student);
        studentNaGodini.setGodinaStudija(godinaStudija);

        return studentNaGodini;
    }
}
