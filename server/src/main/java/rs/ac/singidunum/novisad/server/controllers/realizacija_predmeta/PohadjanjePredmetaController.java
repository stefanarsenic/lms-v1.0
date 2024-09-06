package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PolaganjePredmetaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.StudentInfoDto;
import rs.ac.singidunum.novisad.server.dto.student.PohadjanjePredmetaDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolaganjePredmeta;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PohadjanjePredmetaRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.PolaganjePredmetaRepository;
import rs.ac.singidunum.novisad.server.repositories.student.StudentNaGodiniRepository;
import rs.ac.singidunum.novisad.server.services.student.PohadjanjePredmetaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pohadjanje-predmeta")
@Secured({"ROLE_SLUZBA","ROLE_ADMIN","ROLE_STUDENT","ROLE_NASTAVNIK"})
public class PohadjanjePredmetaController extends GenericController<PohadjanjePredmeta, Long, PohadjanjePredmetaDto> {
    private final PohadjanjePredmetaService pohadjanjePredmetaService;
    private final PohadjanjePredmetaRepository pohadjanjePredmetaRepository;
    private final StudentNaGodiniRepository studentNaGodiniRepository;
    private final PolaganjePredmetaRepository polaganjePredmetaRepository;

    public PohadjanjePredmetaController(GenericService<PohadjanjePredmeta, Long> service, PohadjanjePredmetaService pohadjanjePredmetaService,
                                        PohadjanjePredmetaRepository pohadjanjePredmetaRepository,
                                        StudentNaGodiniRepository studentNaGodiniRepository,
                                        PolaganjePredmetaRepository polaganjePredmetaRepository) {
        super(service);
        this.pohadjanjePredmetaService = pohadjanjePredmetaService;
        this.pohadjanjePredmetaRepository = pohadjanjePredmetaRepository;
        this.studentNaGodiniRepository = studentNaGodiniRepository;
        this.polaganjePredmetaRepository = polaganjePredmetaRepository;
    }

    @GetMapping("/student-info")
    public ResponseEntity<StudentInfoDto> getStudentInfo(@PathParam("studentId") Long studentId) {
        StudentNaGodini studentNaGodini = studentNaGodiniRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Integer ects = pohadjanjePredmetaService.findOstvareniEspbByStudentId(studentId);
        Double prosecnaOcena = pohadjanjePredmetaRepository.getAverageKonacnaOcenaByStudentId(studentId)
                .orElseThrow();

        List<PolaganjePredmetaDto> neuspesnaDtos = mapToPolaganjePredmetaDtos(
                polaganjePredmetaRepository.findNeuspesnaPolaganjaByStudentId(studentId),
                studentNaGodini
        );

        List<PohadjanjePredmetaDto> dtos = mapToPohadjanjePredmetaDtos(
                pohadjanjePredmetaRepository.findPolozeniPredmetiByStudent(studentId),
                studentNaGodini
        );

        StudentInfoDto studentInfoDto = new StudentInfoDto();
        studentInfoDto.setPolozeniPredmeti(dtos);
        studentInfoDto.setNeuspesnaPolaganja(neuspesnaDtos);
        studentInfoDto.setEcts(ects);
        studentInfoDto.setProsecnaOcena(prosecnaOcena);

        return ResponseEntity.ok(studentInfoDto);
    }

    private List<PolaganjePredmetaDto> mapToPolaganjePredmetaDtos(List<PolaganjePredmeta> polaganja, StudentNaGodini studentNaGodini) {
        return polaganja.stream().map(polaganje -> {
            try {
                PolaganjePredmetaDto dto = new PolaganjePredmetaDto();
                dto.setKonacnaOcena(polaganje.getKonacnaOcena());
                studentNaGodini.setPredmeti(null);
                dto.setStudent(EntityDtoMapper.convertToDto(studentNaGodini, StudentNaGodiniDto.class));
                polaganje.getPredmet().setSilabus(null);
                dto.setPredmet(EntityDtoMapper.convertToDto(polaganje.getPredmet(), PredmetDto.class));
                dto.getPredmet().setNastavnik(EntityDtoMapper.convertToDto(polaganje.getPredmet().getNastavnik(), NastavnikDto.class));
                dto.getPredmet().setAsistent(EntityDtoMapper.convertToDto(polaganje.getPredmet().getAsistent(), NastavnikDto.class));
                dto.getPredmet().getNastavnik().setPravoPristupaSet(null);
                dto.getPredmet().getAsistent().setPravoPristupaSet(null);
                dto.setBrojPolaganja(polaganjePredmetaRepository.findBrojPolaganjaByStudentAndPredmet(studentNaGodini.getId(), dto.getPredmet().getId()));
                dto.setBodovi(polaganjePredmetaRepository.getBodoviPolaganjaByPredmetAndStudent(studentNaGodini.getId(), dto.getPredmet().getId()));

                return dto;
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    private List<PohadjanjePredmetaDto> mapToPohadjanjePredmetaDtos(List<PohadjanjePredmeta> pohadjanja, StudentNaGodini studentNaGodini) {
        return pohadjanja.stream().map(pohadjanje -> {
            try {
                PohadjanjePredmetaDto dto = new PohadjanjePredmetaDto();
                dto.setKonacnaOcena(pohadjanje.getKonacnaOcena());
                studentNaGodini.setPredmeti(null);
                dto.setStudent(EntityDtoMapper.convertToDto(studentNaGodini, StudentNaGodiniDto.class));
                pohadjanje.getPredmet().setSilabus(null);
                dto.setPredmet(EntityDtoMapper.convertToDto(pohadjanje.getPredmet(), PredmetDto.class));
                dto.getPredmet().setNastavnik(EntityDtoMapper.convertToDto(pohadjanje.getPredmet().getNastavnik(), NastavnikDto.class));
                dto.getPredmet().setAsistent(EntityDtoMapper.convertToDto(pohadjanje.getPredmet().getAsistent(), NastavnikDto.class));
                dto.getPredmet().getNastavnik().setPravoPristupaSet(null);
                dto.getPredmet().getAsistent().setPravoPristupaSet(null);
                dto.setBrojPolaganja(polaganjePredmetaRepository.findBrojPolaganjaByStudentAndPredmet(studentNaGodini.getId(), dto.getPredmet().getId()));
                dto.setBodovi(pohadjanjePredmetaRepository.getBodoviPolaganjaByPredmetAndStudent(studentNaGodini.getId(), dto.getPredmet().getId()));

                return dto;
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }


    @GetMapping("/ukupno-espb/student/{studentId}")
    public ResponseEntity<Integer> getUkupnoEspbStudenta(@PathVariable Long studentId){
        Integer ukupnoEspb = pohadjanjePredmetaService.findOstvareniEspbByStudentId(studentId);

        return new ResponseEntity<>(ukupnoEspb, HttpStatus.OK);
    }
    @GetMapping("/prosecna-ocena/student/{studentId}")
    public ResponseEntity<Optional<Double>> getProsecnaOcenaStudenta(@PathVariable Long studentId){
        Optional<Double> prosecnaOcena = pohadjanjePredmetaService.averageKonacnaOcenaByStudentId(studentId);

        return new ResponseEntity<>(prosecnaOcena, HttpStatus.OK);
    }
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PohadjanjePredmetaDto>> getPolozeniPredmetiByStudentBrojIndeksa(@PathVariable Long studentId){
        List<PohadjanjePredmeta> polozeniPredmeti = pohadjanjePredmetaService.findPolozeniPredmetsByStudentId(studentId);
        List<PohadjanjePredmetaDto> dtos = polozeniPredmeti.stream().map(polozeniPredmet -> {
            try {
                PohadjanjePredmetaDto dto = EntityDtoMapper.convertToDto(polozeniPredmet, PohadjanjePredmetaDto.class);
                dto.setPredmet(EntityDtoMapper.convertToDto(polozeniPredmet.getPredmet(), PredmetDto.class));
                dto.getPredmet().setSilabus(null);
                return dto;
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    protected PohadjanjePredmetaDto convertToDto(PohadjanjePredmeta entity) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    protected PohadjanjePredmeta convertToEntity(PohadjanjePredmetaDto dto) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK","ROLE_SLUZBA"})
    public ResponseEntity<PohadjanjePredmetaDto> create(PohadjanjePredmetaDto dto) throws IllegalAccessException, InstantiationException {
        return super.create(dto);
    }

    @Override
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK","ROLE_SLUZBA"})
    public ResponseEntity<PohadjanjePredmetaDto> update(Long aLong, PohadjanjePredmetaDto dto) throws IllegalAccessException, InstantiationException {
        return super.update(aLong, dto);
    }

    @Override
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK","ROLE_SLUZBA"})
    public ResponseEntity<Void> delete(Long aLong) {
        return super.delete(aLong);
    }
}
