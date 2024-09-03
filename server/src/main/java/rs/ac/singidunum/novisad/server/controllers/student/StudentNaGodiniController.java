package rs.ac.singidunum.novisad.server.controllers.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;
import rs.ac.singidunum.novisad.server.dto.adresa.MestoDto;
import rs.ac.singidunum.novisad.server.dto.fakultet.FakultetDto;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetPlanaZaGodinuDto;
import rs.ac.singidunum.novisad.server.dto.student.PohadjanjePredmetaDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.services.student.StudentNaGodiniService;
import rs.ac.singidunum.novisad.server.services.student.StudentService;

import java.util.*;

@RestController
@RequestMapping("/api/student-na-godini")
public class StudentNaGodiniController extends GenericController<StudentNaGodini, Long, StudentNaGodiniDto> {
    private final StudentService studentService;
    private final StudentNaGodiniService studentNaGodiniService;
    public StudentNaGodiniController(GenericService<StudentNaGodini, Long> service, StudentService studentService, StudentNaGodiniService studentNaGodiniService) {
        super(service);
        this.studentService = studentService;
        this.studentNaGodiniService = studentNaGodiniService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentNaGodiniDto>> getStudentiNaGodiniByStudentId(@PathVariable Long studentId) throws IllegalAccessException, InstantiationException {
        List<StudentNaGodini> studentiNaGodini = studentNaGodiniService.findAllByStudentId(studentId);
        List<StudentNaGodiniDto> dtos = new ArrayList<>(Collections.emptySet());
        for (StudentNaGodini entity : studentiNaGodini) {
            StudentNaGodiniDto dto = convertToDto(entity);
            dtos.add(dto);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/student/{username}/student-na-godini")
    public ResponseEntity<List<StudentNaGodiniDto>>findAllByStudentUsername(@PathVariable String username) throws IllegalAccessException, InstantiationException {
        List<StudentNaGodini> studentiNaGodini = studentNaGodiniService.findAllByStudentUsername(username);
        List<StudentNaGodiniDto> dtos = new ArrayList<>(Collections.emptySet());
        for (StudentNaGodini entity : studentiNaGodini) {
            StudentNaGodiniDto dto = convertToDto(entity);
            dtos.add(dto);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<StudentNaGodiniDto> create(@RequestBody StudentNaGodiniDto dto) throws IllegalAccessException, InstantiationException {
        StudentNaGodini entity = convertToEntity(dto);
        entity = studentNaGodiniService.saveWithUpis(entity);
        if(entity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StudentNaGodiniDto savedDto = convertToDto(entity);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentNaGodiniDto> update(@PathVariable Long id, @RequestBody StudentNaGodiniDto dto) throws IllegalAccessException, InstantiationException {
        Optional<StudentNaGodini> entityOptional = service.findById(id);
        if (entityOptional.isPresent()) {
            StudentNaGodini entity = convertToEntity(dto);
            entity = studentNaGodiniService.updateWithUpis(entity);
            StudentNaGodiniDto updatedDto = convertToDto(entity);
            return ResponseEntity.ok(updatedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    protected StudentNaGodiniDto convertToDto(StudentNaGodini entity) throws IllegalAccessException, InstantiationException {
        StudentNaGodiniDto s = EntityDtoMapper.convertToDto(entity, StudentNaGodiniDto.class);
        StudentDto studentDto = EntityDtoMapper.convertToDto(entity.getStudent(), StudentDto.class);
        s.setPredmeti(Collections.emptyList());
        s.setStudijskiProgram(EntityDtoMapper.convertToDto(entity.getStudijskiProgram(), StudijskiProgramDto.class));
        s.setStudent(studentDto);

        List<PohadjanjePredmetaDto> pohadjanjaDto = new ArrayList<>();

        for(PohadjanjePredmeta pohadjanje : entity.getPredmeti()){
            NastavnikDto nastavnikDto = EntityDtoMapper.convertToDto(pohadjanje.getPredmet().getNastavnik(), NastavnikDto.class);
            NastavnikDto asistentDto = EntityDtoMapper.convertToDto(pohadjanje.getPredmet().getAsistent(), NastavnikDto.class);

            Set<IshodDto> silabusDto = new HashSet<>(Collections.emptySet());
            Set<PredmetDto> preduslovDto = new HashSet<>(Collections.emptySet());
            Set<PredmetPlanaZaGodinuDto> planoviDto = new HashSet<>(Collections.emptySet());

            if(pohadjanje.getPredmet().getSilabus() != null){
                for(Ishod ishod : pohadjanje.getPredmet().getSilabus()){
                    ishod.setPredmet(null);
                    silabusDto.add(EntityDtoMapper.convertToDto(ishod, IshodDto.class));
                }
            }
            if(pohadjanje.getPredmet().getPreduslov() != null){
                for(Predmet predmet1 : pohadjanje.getPredmet().getPreduslov()){
                    preduslovDto.add(EntityDtoMapper.convertToDto(predmet1, PredmetDto.class));
                }
            }

            PohadjanjePredmetaDto dto = EntityDtoMapper.convertToDto(pohadjanje, PohadjanjePredmetaDto.class);
            nastavnikDto.setPravoPristupaSet(new HashSet<>());
            asistentDto.setPravoPristupaSet(new HashSet<>());
            dto.getPredmet().setSilabus(silabusDto);
            dto.getPredmet().setPreduslov(preduslovDto);
            dto.getPredmet().setPlanovi(planoviDto);
            dto.getPredmet().setAsistent(asistentDto);
            dto.getPredmet().setNastavnik(nastavnikDto);
            pohadjanjaDto.add(dto);
        }

        s.setPredmeti(pohadjanjaDto);

        return s;
    }

    @Override
    protected StudentNaGodini convertToEntity(StudentNaGodiniDto dto) throws IllegalAccessException, InstantiationException {
        Student student = studentService.findById(dto.getStudent().getId()).orElseThrow();

        StudentNaGodini studentNaGodini = EntityDtoMapper.convertToDto(dto, StudentNaGodini.class);
        studentNaGodini.setStudent(student);
        studentNaGodini.setStudijskiProgram(EntityDtoMapper.convertToEntity(dto.getStudijskiProgram(), StudijskiProgram.class));

        return studentNaGodini;
    }
}
