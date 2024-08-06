package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetPlanaZaGodinuDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.predmet.PredmetPlanaZaGodinu;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.services.korisnik.KorisnikService;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;
import rs.ac.singidunum.novisad.server.services.predmet.IshodService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetPlanaZaGodinuService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;

import java.util.*;

@RestController
@RequestMapping("/api/predmet")
public class PredmetController extends GenericController<Predmet, Long, PredmetDto>{

    private final IshodService ishodService;
    private final PredmetService predmetService;
    private final PredmetPlanaZaGodinuService predmetPlanaZaGodinuService;

    @Autowired
    NastavnikService nastavnikService;
    public PredmetController(GenericService<Predmet, Long> service, IshodService ishodService, PredmetService predmetService, PredmetPlanaZaGodinuService predmetPlanaZaGodinuService) {
        super(service);
        this.ishodService = ishodService;
        this.predmetService = predmetService;
        this.predmetPlanaZaGodinuService = predmetPlanaZaGodinuService;
    }

    @GetMapping("/studijski-program/{studijskiProgramId}")
    public ResponseEntity<List<PredmetDto>> getPredmetiByStudijskiProgram(@PathVariable Long studijskiProgramId){
        List<Predmet> predmeti = predmetService.findPredmetiByStudijskiProgram(studijskiProgramId);
        return PredmetiToDto(predmeti);
    }
    @GetMapping("/studijski-program/{studijskiProgramId}/godina/{godina}")
    public ResponseEntity<List<PredmetDto>> getPredmetiByStudijskiProgramAndGodina(
            @PathVariable Long studijskiProgramId,
            @PathVariable Integer godina){
        List<Predmet> predmeti = predmetService.findPredmetiByStudijskiProgramAndGodina(studijskiProgramId, godina);
        return PredmetiToDto(predmeti);
    }
    @GetMapping("/{predmetId}/esbp")
    public ResponseEntity<Integer> getEsbp(@PathVariable Long predmetId) throws IllegalAccessException, InstantiationException {
        Predmet predmet = predmetService.findById(predmetId).orElseThrow();
        return new ResponseEntity<>(predmet.getEspb(), HttpStatus.OK);
    }

    @GetMapping("/predmeti")
    public ResponseEntity<List<PredmetDto>> getNastavnikPredmeti(@RequestParam("korisnickoIme") String korisnickoIme)
            throws IllegalAccessException, InstantiationException {

        Optional<Nastavnik> nastavnik = nastavnikService.findByKorisnickoIme(korisnickoIme);
        List<Predmet> predmets = new ArrayList<>();
        if (nastavnik.isPresent()) {
            predmets = predmetService.getPredmetByNastavnik(nastavnik.get());
        }

        return PredmetiToDto(predmets);
    }

    @Override
    protected PredmetDto convertToDto(Predmet entity) throws IllegalAccessException, InstantiationException {
        NastavnikDto nastavnikDto = EntityDtoMapper.convertToDto(entity.getNastavnik(), NastavnikDto.class);
        NastavnikDto asistentDto = EntityDtoMapper.convertToDto(entity.getAsistent(), NastavnikDto.class);

        Set<IshodDto> silabusDto = new HashSet<>(Collections.emptySet());
        Set<PredmetDto> preduslovDto = new HashSet<>(Collections.emptySet());
        Set<PredmetPlanaZaGodinuDto> planoviDto = new HashSet<>(Collections.emptySet());

        if(entity.getSilabus() != null){
            for(Ishod ishod : entity.getSilabus()){
                ishod.setPredmet(null);
                silabusDto.add(EntityDtoMapper.convertToDto(ishod, IshodDto.class));
            }
        }
        if(entity.getPreduslov() != null){
            for(Predmet predmet : entity.getPreduslov()){
                preduslovDto.add(EntityDtoMapper.convertToDto(predmet, PredmetDto.class));
            }
        }

        PredmetDto predmetDto = EntityDtoMapper.convertToDto(entity, PredmetDto.class);
        predmetDto.setSilabus(silabusDto);
        predmetDto.setPreduslov(preduslovDto);
        predmetDto.setPlanovi(planoviDto);

        return predmetDto;
    }

    //TODO:Proveri da dobavlja samo studente koji nisu polozili predmet
    @GetMapping("/{predmetId}/students")
    public ResponseEntity<List<StudentDto>> getStudentsByPredmet(@PathVariable Long predmetId) throws IllegalAccessException, InstantiationException {
        List<Student> students = predmetService.getStudentsByPredmet(predmetId);
        List<StudentDto> studentDtos=new ArrayList<>();
        for(Student student:students){
            studentDtos.add(EntityDtoMapper.convertToDto(student,StudentDto.class));
        }
        return ResponseEntity.ok(studentDtos);
    }

    @Override
    protected Predmet convertToEntity(PredmetDto dto) throws IllegalAccessException, InstantiationException {
        Set<Ishod> silabus = new HashSet<>(Collections.emptySet());
        Set<Predmet> preduslov = new HashSet<>(Collections.emptySet());
        Set<PredmetPlanaZaGodinu> planovi = new HashSet<>(Collections.emptySet());

        if(dto.getSilabus() != null) {
            for (IshodDto ishodDto : dto.getSilabus()) {
                silabus.add(ishodService.findById(ishodDto.getId()).orElseThrow());
            }
        }
        if(dto.getPreduslov() != null) {
            for(PredmetDto predmetDto : dto.getPreduslov()){
                preduslov.add(predmetService.findById(predmetDto.getId()).orElseThrow());
            }
        }
        if(dto.getPlanovi() != null){
            for(PredmetPlanaZaGodinuDto p : dto.getPlanovi()){
                planovi.add(predmetPlanaZaGodinuService.findById(p.getId()).orElseThrow());
            }
        }

        Predmet predmet = EntityDtoMapper.convertToEntity(dto, Predmet.class);
        predmet.setSilabus(silabus);
        predmet.setPreduslov(preduslov);

        return predmet;
    }

    private ResponseEntity<List<PredmetDto>> PredmetiToDto(List<Predmet> predmeti) {
        List<PredmetDto> predmetiDto = predmeti.stream().map(predmet -> {
            try {
                return EntityDtoMapper.convertToDto(predmet, PredmetDto.class);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return new ResponseEntity<>(predmetiDto, HttpStatus.OK);
    }


}
