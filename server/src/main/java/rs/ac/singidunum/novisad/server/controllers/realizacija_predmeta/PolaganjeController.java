package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.EvaluacijaZnanjaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.IspitniRokDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PolaganjeDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PrijavljeniIspitDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.RealizacijaPredmetaDto;
import rs.ac.singidunum.novisad.server.dto.student.TipPolaganjaDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.*;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.*;
import rs.ac.singidunum.novisad.server.repositories.student.StudentNaGodiniRepository;

import java.util.List;

@RestController
@RequestMapping("/api/polaganje")
@Secured({"ROLE_ADMIN","ROLE_NASTAVNIK","ROLE_SLUZBA","ROLE_STUDENT"})
public class PolaganjeController extends GenericController<Polaganje, Long, PolaganjeDto> {

    private final StudentNaGodiniRepository studentNaGodiniRepository;
    private final PredmetRepository predmetRepository;
    private final RealizacijaPredmetaRepository realizacijaPredmetaRepository;
    private final EvaluacijaZnanjaRepository evaluacijaZnanjaRepository;
    private final IspitniRokRepository ispitniRokRepository;
    private final TipEvaluacijeRepository tipEvaluacijeRepository;
    private final TipPolaganjaRepository tipPolaganjaRepository;
    private final PolaganjeRepository polaganjeRepository;

    public PolaganjeController(GenericService<Polaganje, Long> service,
                               StudentNaGodiniRepository studentNaGodiniRepository,
                               PredmetRepository predmetRepository,
                               RealizacijaPredmetaRepository realizacijaPredmetaRepository,
                               EvaluacijaZnanjaRepository evaluacijaZnanjaRepository,
                               IspitniRokRepository ispitniRokRepository,
                               TipEvaluacijeRepository tipEvaluacijeRepository,
                               TipPolaganjaRepository tipPolaganjaRepository,
                               PolaganjeRepository polaganjeRepository) {
        super(service);
        this.studentNaGodiniRepository = studentNaGodiniRepository;
        this.predmetRepository = predmetRepository;
        this.realizacijaPredmetaRepository = realizacijaPredmetaRepository;
        this.evaluacijaZnanjaRepository = evaluacijaZnanjaRepository;
        this.ispitniRokRepository = ispitniRokRepository;
        this.tipEvaluacijeRepository = tipEvaluacijeRepository;
        this.tipPolaganjaRepository = tipPolaganjaRepository;
        this.polaganjeRepository = polaganjeRepository;
    }

    @GetMapping("/latest-polaganja-by-student")
    public ResponseEntity<?> getLatestPolaganjaByStudent(
            @PathParam("studentId") Long studentId,
            @PathParam("predmetId") Long predmetId){
        StudentNaGodini studentNaGodini = studentNaGodiniRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));

        List<Polaganje> polaganja = polaganjeRepository.findLatestDistinctPolaganjaByStudentIdAndPredmetId(studentNaGodini.getId(), predmetId);
        List<PolaganjeDto> dtos = polaganja.stream().map(polaganje -> {
            PolaganjeDto p = null;
            try {
                p = convertToDto(polaganje);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
            return p;
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/prijavljeni-ispiti-by-student")
    public ResponseEntity<?> getPrijavljeniIspitiStudenta(@PathParam("studentId") Long studentId){
        StudentNaGodini studentNaGodini = studentNaGodiniRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));

        List<Polaganje> prijavljeniIspiti = polaganjeRepository.findPrijavljeniIspitiByStudentId(studentId);
        List<PrijavljeniIspitDto> dtos = prijavljeniIspiti.stream().map(polaganje -> {
            try {
                PolaganjeDto polaganjeDto = convertToDto(polaganje);
                PrijavljeniIspitDto dto = new PrijavljeniIspitDto();
                dto.setId(polaganjeDto.getId());
                dto.setPredmet(polaganjeDto.getEvaluacijaZnanja().getRealizacijaPredmeta().getPredmet());
                dto.setEvaluacijaZnanja(polaganjeDto.getEvaluacijaZnanja());

                return dto;

            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/by-student")
    @Secured({"ROLE_SLUZBA","ROLE_ADMIN","ROLE_STUDENT","ROLE_NASTAVNIK"})
    public ResponseEntity<?> createPolaganjeIspita(
            @PathParam("studentId") Long studentId,
            @PathParam("predmetId") Long predmetId,
            @PathParam("ispitniRokId") Long ispitniRokId
    ){
        StudentNaGodini studentNaGodini = studentNaGodiniRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Predmet predmet = predmetRepository.findById(predmetId).orElseThrow(() -> new EntityNotFoundException("Predmet not found"));
        RealizacijaPredmeta realizacijaPredmeta = realizacijaPredmetaRepository.findRealizacijaPredmetaByPredmetId(predmetId);
        IspitniRok ispitniRok = ispitniRokRepository.findById(ispitniRokId).orElseThrow(() -> new EntityNotFoundException("Ispitni rok not found"));
        TipEvaluacije tipEvaluacije = tipEvaluacijeRepository.findByNaziv("Usmeni Ispit");
        EvaluacijaZnanja evaluacijaZnanja = evaluacijaZnanjaRepository
                .findEvaluacijaZnanjaByRealizacijaPredmetaAndIspitniRokAndTipEvaluacije(realizacijaPredmeta, ispitniRok, tipEvaluacije)
                .orElseThrow(() -> new EntityNotFoundException("Evaluacija znanja not found"));

        TipPolaganja tipPolaganja = tipPolaganjaRepository.findById(3L).orElseThrow(() -> new EntityNotFoundException("Tip polaganja not found"));

        Polaganje polaganje = new Polaganje();
        polaganje.setBodovi(null);
        polaganje.setStudent(studentNaGodini);
        polaganje.setEvaluacijaZnanja(evaluacijaZnanja);
        polaganje.setTipPolaganja(tipPolaganja);

        Polaganje saved = polaganjeRepository.save(polaganje);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/kolokvijum/by-student")
    public ResponseEntity<?> createPolaganjeKolokvijuma(
            @PathParam("studentId") Long studentId,
            @PathParam("predmetId") Long predmetId,
            @PathParam("tipPolaganjaId") Long tipPolaganjaId,
            @PathParam("evaluacijaZnanjaId") Long evaluacijaZnanjaId,
            @RequestBody PolaganjeDto polaganjeDto
    ){
        StudentNaGodini studentNaGodini = studentNaGodiniRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Predmet predmet = predmetRepository.findById(predmetId).orElseThrow(() -> new EntityNotFoundException("Predmet not found"));
        RealizacijaPredmeta realizacijaPredmeta = realizacijaPredmetaRepository.findRealizacijaPredmetaByPredmetId(predmetId);
        TipEvaluacije tipEvaluacije = tipEvaluacijeRepository.findByNaziv("Kolokvijum");
        EvaluacijaZnanja evaluacijaZnanja = evaluacijaZnanjaRepository
                .findById(evaluacijaZnanjaId)
                .orElseThrow(() -> new EntityNotFoundException("Evaluacija znanja not found"));

        TipPolaganja tipPolaganja = tipPolaganjaRepository.findById(tipPolaganjaId).orElseThrow(() -> new EntityNotFoundException("Tip polaganja not found"));

        Polaganje polaganje = new Polaganje();
        polaganje.setBodovi(polaganjeDto.getBodovi());
        polaganje.setStudent(studentNaGodini);
        polaganje.setEvaluacijaZnanja(evaluacijaZnanja);
        polaganje.setTipPolaganja(tipPolaganja);

        Polaganje saved = polaganjeRepository.save(polaganje);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/update-bodovi")
    public ResponseEntity<?> updateBodovi(@PathParam("polaganjeId") Long polaganjeId, @RequestBody PolaganjeDto polaganjeDto) throws IllegalAccessException, InstantiationException {
        Polaganje polaganje = polaganjeRepository.findById(polaganjeId).orElseThrow(() -> new EntityNotFoundException("Polaganje not found"));
        polaganje.setBodovi(polaganjeDto.getBodovi());
        polaganje.setNapomena(polaganjeDto.getNapomena());

        Polaganje saved = polaganjeRepository.save(polaganje);
        PolaganjeDto dto = convertToDto(saved);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @Override
    @Secured({"ROLE_NASTAVNIK","ROLE_SLUZBA","ROLE_ADMIN"})
    public ResponseEntity<Void> delete(Long aLong) {
        return super.delete(aLong);
    }

    @Override
    @Secured({"ROLE_NASTAVNIK","ROLE_SLUZBA","ROLE_ADMIN"})
    public ResponseEntity<PolaganjeDto> update(Long aLong, PolaganjeDto dto) throws IllegalAccessException, InstantiationException {
        return super.update(aLong, dto);
    }

    @Override
    protected PolaganjeDto convertToDto(Polaganje entity) throws IllegalAccessException, InstantiationException {
        entity.getEvaluacijaZnanja().getRealizacijaPredmeta().getPredmet().getNastavnik().setPravoPristupaSet(null);
        entity.getEvaluacijaZnanja().getRealizacijaPredmeta().getPredmet().getAsistent().setPravoPristupaSet(null);
        entity.getEvaluacijaZnanja().getRealizacijaPredmeta().getPredmet().setSilabus(null);
        entity.getEvaluacijaZnanja().getRealizacijaPredmeta().setObavestenja(null);

        PolaganjeDto polaganjeDto = EntityDtoMapper.convertToDto(entity, PolaganjeDto.class);
        if(entity.getTipPolaganja() != null) {
            polaganjeDto.setTipPolaganja(EntityDtoMapper.convertToDto(entity.getTipPolaganja(), TipPolaganjaDto.class));
        }
        EvaluacijaZnanjaDto evaluacijaZnanjaDto = EntityDtoMapper.convertToDto(entity.getEvaluacijaZnanja(), EvaluacijaZnanjaDto.class);
        evaluacijaZnanjaDto.setIspitniRok(EntityDtoMapper.convertToDto(entity.getEvaluacijaZnanja().getIspitniRok(), IspitniRokDto.class));
        RealizacijaPredmetaDto realizacijaPredmetaDto = EntityDtoMapper.convertToDto(entity.getEvaluacijaZnanja().getRealizacijaPredmeta(), RealizacijaPredmetaDto.class);
        PredmetDto predmetDto = EntityDtoMapper.convertToDto(entity.getEvaluacijaZnanja().getRealizacijaPredmeta().getPredmet(), PredmetDto.class);
        NastavnikDto nastavnik = EntityDtoMapper.convertToDto(entity.getEvaluacijaZnanja().getRealizacijaPredmeta().getPredmet().getNastavnik(), NastavnikDto.class);
        NastavnikDto asistent = EntityDtoMapper.convertToDto(entity.getEvaluacijaZnanja().getRealizacijaPredmeta().getPredmet().getAsistent(), NastavnikDto.class);

        predmetDto.setNastavnik(nastavnik);
        predmetDto.setAsistent(asistent);
        realizacijaPredmetaDto.setPredmet(predmetDto);
        evaluacijaZnanjaDto.setRealizacijaPredmeta(realizacijaPredmetaDto);
        polaganjeDto.setEvaluacijaZnanja(evaluacijaZnanjaDto);

        return polaganjeDto;
    }

    @Override
    protected Polaganje convertToEntity(PolaganjeDto dto) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
