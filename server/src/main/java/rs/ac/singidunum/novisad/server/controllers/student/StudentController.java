package rs.ac.singidunum.novisad.server.controllers.student;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;
import rs.ac.singidunum.novisad.server.dto.adresa.MestoDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.adresa.Mesto;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.services.adresa.AdresaService;
import rs.ac.singidunum.novisad.server.services.student.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController extends GenericController<Student, Long, StudentDto> {
    private final AdresaService adresaService;
    private final StudentService studentService;
    public StudentController(GenericService<Student, Long> service, AdresaService adresaService, StudentService studentService) {
        super(service);
        this.adresaService = adresaService;
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}/drzava")
    public ResponseEntity<String> getNazivDrzaveByStudentId(@PathVariable Long studentId){
        String drzava = studentService.findNazivDrzaveByStudentId(studentId);

        return new ResponseEntity<>(drzava, HttpStatus.OK);
    }

    @Override
    protected StudentDto convertToDto(Student entity) throws IllegalAccessException, InstantiationException {
        StudentDto studentDto = EntityDtoMapper.convertToDto(entity, StudentDto.class);
        AdresaDto adresaDto = EntityDtoMapper.convertToDto(entity.getAdresa(), AdresaDto.class);
        MestoDto mestoDto=EntityDtoMapper.convertToDto(entity.getAdresa().getMesto(),MestoDto.class);
        adresaDto.setMesto(mestoDto);
        studentDto.setAdresa(adresaDto);

        return studentDto;
    }

    @Override
    protected Student convertToEntity(StudentDto dto) throws IllegalAccessException, InstantiationException {
        Adresa adresa = adresaService.findById(dto.getAdresa().getId()).orElseThrow();

        Student student = EntityDtoMapper.convertToEntity(dto, Student.class);
        student.setAdresa(adresa);

        return student;
    }

    @Override
    @RequestMapping(path = "/dodaj",method = RequestMethod.POST)
    public ResponseEntity<StudentDto> create(StudentDto dto) throws IllegalAccessException, InstantiationException {
        Mesto mesto=EntityDtoMapper.convertToEntity(dto.getAdresa().getMesto(),Mesto.class);
        Adresa adresa=EntityDtoMapper.convertToEntity(dto.getAdresa(),Adresa.class);
        adresa.setMesto(mesto);
        Adresa adresa1= adresaService.save(adresa);
        AdresaDto adresaDto=EntityDtoMapper.convertToDto(adresa1,AdresaDto.class);
        dto.setAdresa(adresaDto);
        return super.create(dto);
    }
}
