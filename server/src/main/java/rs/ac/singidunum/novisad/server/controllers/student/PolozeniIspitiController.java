package rs.ac.singidunum.novisad.server.controllers.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.IspitDto;
import rs.ac.singidunum.novisad.server.dto.student.PolozeniIspitiDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Ispit;
import rs.ac.singidunum.novisad.server.model.student.PolozeniIspiti;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.services.predmet.IspitService;
import rs.ac.singidunum.novisad.server.services.student.StudentService;

@RestController
@RequestMapping("/api/polozeni-ispiti")
public class PolozeniIspitiController extends GenericController<PolozeniIspiti, Long, PolozeniIspitiDto> {

    private final StudentService studentService;
    private final IspitService ispitService;
    public PolozeniIspitiController(GenericService<PolozeniIspiti, Long> service, StudentService studentService, IspitService ispitService) {
        super(service);
        this.studentService = studentService;
        this.ispitService = ispitService;
    }

    @Override
    protected PolozeniIspitiDto convertToDto(PolozeniIspiti entity) throws IllegalAccessException, InstantiationException {
        PolozeniIspitiDto p = EntityDtoMapper.convertToDto(entity, PolozeniIspitiDto.class);
        StudentDto studentDto = EntityDtoMapper.convertToDto(entity.getStudent(), StudentDto.class);
        IspitDto ispitDto = EntityDtoMapper.convertToDto(entity.getIspit(), IspitDto.class);

        p.setStudent(studentDto);
        p.setIspit(ispitDto);

        return p;
    }

    @Override
    protected PolozeniIspiti convertToEntity(PolozeniIspitiDto dto) throws IllegalAccessException, InstantiationException {
        Student student = studentService.findById(dto.getStudent().getId()).orElseThrow();
        Ispit ispit = ispitService.findById(dto.getIspit().getId()).orElseThrow();

        PolozeniIspiti p = EntityDtoMapper.convertToEntity(dto, PolozeniIspiti.class);
        p.setStudent(student);
        p.setIspit(ispit);

        return p;
    }
}
