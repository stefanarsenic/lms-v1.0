package rs.ac.singidunum.novisad.server.controllers.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;
import rs.ac.singidunum.novisad.server.dto.adresa.MestoDto;
import rs.ac.singidunum.novisad.server.dto.fakultet.FakultetDto;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.services.student.StudentNaGodiniService;
import rs.ac.singidunum.novisad.server.services.student.StudentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student-na-godini")
public class StudentNaGodiniController extends GenericController<StudentNaGodini, Long, StudentNaGodiniDto> {

    private final StudentService studentService;
    public StudentNaGodiniController(GenericService<StudentNaGodini, Long> service, StudentService studentService) {
        super(service);
        this.studentService = studentService;
    }

    @Override
    protected StudentNaGodiniDto convertToDto(StudentNaGodini entity) throws IllegalAccessException, InstantiationException {
        StudentNaGodiniDto s = EntityDtoMapper.convertToDto(entity, StudentNaGodiniDto.class);
        StudentDto studentDto = EntityDtoMapper.convertToDto(entity.getStudent(), StudentDto.class);

        s.setStudijskiProgram(EntityDtoMapper.convertToDto(entity.getStudijskiProgram(), StudijskiProgramDto.class));
        s.setStudent(studentDto);

        return s;
    }

    @Override
    protected StudentNaGodini convertToEntity(StudentNaGodiniDto dto) throws IllegalAccessException, InstantiationException {
        Student student = studentService.findById(dto.getStudent().getId()).orElseThrow();

        StudentNaGodini studentNaGodini = EntityDtoMapper.convertToDto(dto, StudentNaGodini.class);
        studentNaGodini.setStudent(student);

        return studentNaGodini;
    }
}
