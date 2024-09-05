package rs.ac.singidunum.novisad.server.controllers.student;

import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetPlanaZaGodinuDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PolaganjePredmetaDto;
import rs.ac.singidunum.novisad.server.dto.student.PohadjanjePredmetaDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.dto.student.UpisDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolaganjePredmeta;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.model.student.Upis;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PohadjanjePredmetaRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PolaganjePredmetaRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PolaganjeRepository;
import rs.ac.singidunum.novisad.server.repositories.student.StudentNaGodiniRepository;
import rs.ac.singidunum.novisad.server.repositories.student.UpisRepository;
import rs.ac.singidunum.novisad.server.services.student.StudentNaGodiniService;
import rs.ac.singidunum.novisad.server.services.student.StudentService;

import java.util.*;

@RestController
@RequestMapping("/api/student-na-godini")
public class StudentNaGodiniController extends GenericController<StudentNaGodini, Long, StudentNaGodiniDto> {
    private final StudentService studentService;
    private final StudentNaGodiniService studentNaGodiniService;
    private final StudentNaGodiniRepository studentNaGodiniRepository;
    private final PredmetRepository predmetRepository;
    private final PohadjanjePredmetaRepository pohadjanjePredmetaRepository;
    private final UpisRepository upisRepository;
    private final PolaganjeRepository polaganjeRepository;
    private final PolaganjePredmetaRepository polaganjePredmetaRepository;

    public StudentNaGodiniController(GenericService<StudentNaGodini, Long> service, StudentService studentService, StudentNaGodiniService studentNaGodiniService,
                                     StudentNaGodiniRepository studentNaGodiniRepository,
                                     PredmetRepository predmetRepository,
                                     PohadjanjePredmetaRepository pohadjanjePredmetaRepository,
                                     UpisRepository upisRepository,
                                     PolaganjeRepository polaganjeRepository,
                                     PolaganjePredmetaRepository polaganjePredmetaRepository) {
        super(service);
        this.studentService = studentService;
        this.studentNaGodiniService = studentNaGodiniService;
        this.studentNaGodiniRepository = studentNaGodiniRepository;
        this.predmetRepository = predmetRepository;
        this.pohadjanjePredmetaRepository = pohadjanjePredmetaRepository;
        this.upisRepository = upisRepository;
        this.polaganjeRepository = polaganjeRepository;
        this.polaganjePredmetaRepository = polaganjePredmetaRepository;
    }

    @GetMapping("/upisi-by-student")
    public ResponseEntity<?> getUpisiByStudent(@PathParam("studentId") Long studentId){
        StudentNaGodini student = studentNaGodiniRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        List<Upis> upisi = upisRepository.findAllByStudent(student);
        List<UpisDto> dtos = upisi.stream().map(upis -> {
            try {
                return EntityDtoMapper.convertToDto(upis, UpisDto.class);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/by-predmet")
    public ResponseEntity<List<StudentInfoDto>> getStudentiNaGodiniByPredmet(@PathParam("predmetId") Long predmetId) throws IllegalAccessException, InstantiationException {
        Predmet predmet = predmetRepository.findById(predmetId).orElseThrow(() -> new EntityNotFoundException("Predmet not found with id: " + predmetId.toString()));
        List<StudentInfoDto> studentInfoDtos = new ArrayList<>();
        List<StudentNaGodini> studentiNaGodini = studentNaGodiniRepository.findStudentiNaGodiniByPredmet(predmetId);
        for (StudentNaGodini entity : studentiNaGodini) {
            Double prosecnaOcena = pohadjanjePredmetaRepository.getAverageKonacnaOcenaByStudentId(entity.getId()).orElseThrow();
            Integer ects = pohadjanjePredmetaRepository.getOstvareniEspbByStudentId(entity.getId()).orElseThrow();
            List<Upis> upisi = upisRepository.findAllByStudent(entity);
            List<PolaganjePredmeta> neuspesnaPolaganja = polaganjePredmetaRepository.findNeuspesnaPolaganjaByStudentId(entity.getId());
            List<PohadjanjePredmeta> polozeniPredmeti = pohadjanjePredmetaRepository.findPolozeniPredmetiByStudent(entity.getId());

            StudentNaGodiniDto dto = convertToDto(entity);

            StudentInfoDto studentInfoDto = new StudentInfoDto();
            studentInfoDto.setStudent(dto);
            studentInfoDto.setEcts(ects);
            studentInfoDto.setProsecnaOcena(prosecnaOcena);
            studentInfoDto.setUpisi(upisi);

            if(!neuspesnaPolaganja.isEmpty()){
                List<PolaganjePredmetaDto> neuspesnaPolaganjaDto = neuspesnaPolaganja.stream().map(polaganjePredmeta -> {
                    try {
                        PolaganjePredmetaDto p = EntityDtoMapper.convertToDto(polaganjePredmeta, PolaganjePredmetaDto.class);
                        p.setPredmet(EntityDtoMapper.convertToDto(predmet, PredmetDto.class));

                        return p;
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
                studentInfoDto.setNeuspesnaPolaganja(neuspesnaPolaganjaDto);
            }

            if(!polozeniPredmeti.isEmpty()) {
                List<PohadjanjePredmetaDto> polozeniPredmetiDto = polozeniPredmeti.stream().map(pohadjanjePredmeta -> {
                    try {
                        PohadjanjePredmetaDto p = EntityDtoMapper.convertToDto(pohadjanjePredmeta, PohadjanjePredmetaDto.class);
                        p.setPredmet(EntityDtoMapper.convertToDto(predmet, PredmetDto.class));

                        return p;
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
                studentInfoDto.setPolozeniPredmeti(polozeniPredmetiDto);
            }

            studentInfoDtos.add(studentInfoDto);
        }

        return new ResponseEntity<>(studentInfoDtos, HttpStatus.OK);
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
        if(entity.getStudijskiProgram() != null) {
            s.setStudijskiProgram(EntityDtoMapper.convertToDto(entity.getStudijskiProgram(), StudijskiProgramDto.class));
        }
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
            dto.setPredmet(EntityDtoMapper.convertToDto(pohadjanje.getPredmet(), PredmetDto.class));
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
