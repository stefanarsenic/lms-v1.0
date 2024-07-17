package rs.ac.singidunum.novisad.server.controllers.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.student.AktivnostDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentskaAktivnostDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.Aktivnost;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.model.student.StudentskaAktivnost;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;
import rs.ac.singidunum.novisad.server.services.student.AktivnostService;
import rs.ac.singidunum.novisad.server.services.student.StudentNaGodiniService;

@RestController
@RequestMapping("/api/studentska-aktivnost")
public class StudentskaAktivnostController extends GenericController<StudentskaAktivnost, Long, StudentskaAktivnostDto> {

    private final AktivnostService aktivnostService;
    private final PredmetService predmetService;
    private final StudentNaGodiniService studentNaGodiniService;
    public StudentskaAktivnostController(GenericService<StudentskaAktivnost, Long> service, AktivnostService aktivnostService, PredmetService predmetService, StudentNaGodiniService studentNaGodiniService) {
        super(service);
        this.aktivnostService = aktivnostService;
        this.predmetService = predmetService;
        this.studentNaGodiniService = studentNaGodiniService;
    }

    @Override
    protected StudentskaAktivnostDto convertToDto(StudentskaAktivnost entity) throws IllegalAccessException, InstantiationException {
        StudentskaAktivnostDto s = EntityDtoMapper.convertToDto(entity, StudentskaAktivnostDto.class);
        AktivnostDto aktivnostDto = EntityDtoMapper.convertToDto(entity.getAktivnost(), AktivnostDto.class);
        PredmetDto predmetDto = EntityDtoMapper.convertToDto(entity.getPredmet(), PredmetDto.class);
        StudentNaGodiniDto studentNaGodiniDto = EntityDtoMapper.convertToDto(entity.getStudent(), StudentNaGodiniDto.class);

        s.setAktivnost(aktivnostDto);
        s.setPredmet(predmetDto);
        s.setStudent(studentNaGodiniDto);

        return s;
    }

    @Override
    protected StudentskaAktivnost convertToEntity(StudentskaAktivnostDto dto) throws IllegalAccessException, InstantiationException {
        Aktivnost aktivnost = aktivnostService.findById(dto.getAktivnost().getId()).orElseThrow();
        Predmet predmet = predmetService.findById(dto.getPredmet().getId()).orElseThrow();
        StudentNaGodini studentNaGodini = studentNaGodiniService.findById(dto.getStudent().getId()).orElseThrow();

        StudentskaAktivnost studentskaAktivnost = EntityDtoMapper.convertToEntity(dto, StudentskaAktivnost.class);
        studentskaAktivnost.setAktivnost(aktivnost);
        studentskaAktivnost.setPredmet(predmet);
        studentskaAktivnost.setStudent(studentNaGodini);

        return studentskaAktivnost;
    }
}
