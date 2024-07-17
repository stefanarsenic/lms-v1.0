package rs.ac.singidunum.novisad.server.controllers.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;
import rs.ac.singidunum.novisad.server.dto.student.GodinaStudijaDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.student.GodinaStudija;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.services.adresa.AdresaService;
import rs.ac.singidunum.novisad.server.services.student.GodinaStudijaService;

@RestController
@RequestMapping("/api/student")
public class StudentController extends GenericController<Student, Long, StudentDto> {

    private final AdresaService adresaService;
    private final GodinaStudijaService godinaStudijaService;
    public StudentController(GenericService<Student, Long> service, AdresaService adresaService, GodinaStudijaService godinaStudijaService) {
        super(service);
        this.adresaService = adresaService;
        this.godinaStudijaService = godinaStudijaService;
    }

    @Override
    protected StudentDto convertToDto(Student entity) throws IllegalAccessException, InstantiationException {
        StudentDto studentDto = EntityDtoMapper.convertToDto(entity, StudentDto.class);
        AdresaDto adresaDto = EntityDtoMapper.convertToDto(entity.getAdresa(), AdresaDto.class);
        GodinaStudijaDto godinaStudijaDto = EntityDtoMapper.convertToDto(entity.getGodinaStudija(), GodinaStudijaDto.class);

        studentDto.setAdresa(adresaDto);
        studentDto.setGodinaStudija(godinaStudijaDto);

        return studentDto;
    }

    @Override
    protected Student convertToEntity(StudentDto dto) throws IllegalAccessException, InstantiationException {
        Adresa adresa = adresaService.findById(dto.getAdresa().getId()).orElseThrow();
        GodinaStudija godinaStudija = godinaStudijaService.findById(dto.getGodinaStudija().getId()).orElseThrow();

        Student student = EntityDtoMapper.convertToEntity(dto, Student.class);
        student.setAdresa(adresa);
        student.setGodinaStudija(godinaStudija);

        return student;
    }
}
