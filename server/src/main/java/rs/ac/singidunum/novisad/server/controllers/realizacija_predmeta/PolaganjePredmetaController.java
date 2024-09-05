package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PolaganjeDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PolaganjePredmetaDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.*;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.*;
import rs.ac.singidunum.novisad.server.repositories.student.StudentNaGodiniRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/polaganje-predmeta")
public class PolaganjePredmetaController extends GenericController<PolaganjePredmeta, Long, PolaganjePredmetaDto> {

    private final PredmetRepository predmetRepository;
    private final StudentNaGodiniRepository studentNaGodiniRepository;
    private final PolaganjeRepository polaganjeRepository;
    private final PolaganjePredmetaRepository polaganjePredmetaRepository;
    private final PolaganjePredmetaHasPolaganjeRepository polaganjePredmetaHasPolaganjeRepository;
    private final PohadjanjePredmetaHasPolaganjaRepository pohadjanjePredmetaHasPolaganjaRepository;
    private final PohadjanjePredmetaRepository pohadjanjePredmetaRepository;
    private final IspitniRokRepository ispitniRokRepository;

    public PolaganjePredmetaController(GenericService<PolaganjePredmeta, Long> service,
                                       PredmetRepository predmetRepository,
                                       StudentNaGodiniRepository studentNaGodiniRepository,
                                       PolaganjeRepository polaganjeRepository,
                                       PolaganjePredmetaRepository polaganjePredmetaRepository,
                                       PolaganjePredmetaHasPolaganjeRepository polaganjePredmetaHasPolaganjeRepository,
                                       PohadjanjePredmetaHasPolaganjaRepository pohadjanjePredmetaHasPolaganjaRepository,
                                       PohadjanjePredmetaRepository pohadjanjePredmetaRepository,
                                       IspitniRokRepository ispitniRokRepository) {
        super(service);
        this.predmetRepository = predmetRepository;
        this.studentNaGodiniRepository = studentNaGodiniRepository;
        this.polaganjeRepository = polaganjeRepository;
        this.polaganjePredmetaRepository = polaganjePredmetaRepository;
        this.polaganjePredmetaHasPolaganjeRepository = polaganjePredmetaHasPolaganjeRepository;
        this.pohadjanjePredmetaHasPolaganjaRepository = pohadjanjePredmetaHasPolaganjaRepository;
        this.pohadjanjePredmetaRepository = pohadjanjePredmetaRepository;
        this.ispitniRokRepository = ispitniRokRepository;
    }

    @PostMapping("/arhiviraj")
    public ResponseEntity<?> arhivirajPolaganje(
            @PathParam("studentId") Long studentId,
            @PathParam("predmetId") Long predmetId,
            @RequestBody PolaganjePredmetaDto polaganjePredmetaDto) throws IllegalAccessException, InstantiationException {

        PolaganjePredmeta polaganjePredmeta = new PolaganjePredmeta();
        PohadjanjePredmeta pohadjanjePredmeta = new PohadjanjePredmeta();

        Predmet predmet = predmetRepository.findById(predmetId).orElseThrow(() -> new EntityNotFoundException("Predmet not found"));
        StudentNaGodini student = studentNaGodiniRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));

        IspitniRok ispitniRok = ispitniRokRepository.findById(polaganjePredmetaDto.getPolaganja().get(polaganjePredmetaDto.getPolaganja().size()-1).getEvaluacijaZnanja().getIspitniRok().getId()).orElseThrow(() -> new EntityNotFoundException("Ispitni rok not found"));
        PolaganjePredmeta pp = polaganjePredmetaRepository.findByStudentAndPredmetAndIspitniRok(student, predmet, ispitniRok);
        if(pp != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("message", "Student je vec polagao ovaj predmet u ovom ispitnom roku"));
        }

        LocalDate today = LocalDate.now();
        LocalDate petnaestDanaPosle = ispitniRok.getKraj().plusDays(15);
        if(today.isBefore(ispitniRok.getKraj()) || today.isAfter(petnaestDanaPosle)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("message", "Termin za ocenjivanje je prosao"));
        }

        pohadjanjePredmeta.setPredmet(predmet);
        pohadjanjePredmeta.setStudent(student);
        pohadjanjePredmeta.setIspitniRok(ispitniRok);
        polaganjePredmeta.setKonacnaOcena(polaganjePredmetaDto.getKonacnaOcena());

        polaganjePredmeta.setPredmet(predmet);
        polaganjePredmeta.setStudent(student);
        polaganjePredmeta.setIspitniRok(ispitniRok);
        polaganjePredmeta.setKonacnaOcena(polaganjePredmetaDto.getKonacnaOcena());

        if(polaganjePredmetaDto.getPolaganja().size() != 3){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Polaganje moze da se arhivira samo ukoliko je student polagao sva 3 tipa aktivnosti");
        }

        PolaganjePredmeta saved = polaganjePredmetaRepository.save(polaganjePredmeta);
        if(polaganjePredmetaDto.getKonacnaOcena() >= 6){
            pohadjanjePredmetaRepository.save(pohadjanjePredmeta);
        }
        for(PolaganjeDto p : polaganjePredmetaDto.getPolaganja()){
            if(p.getId() != null){
                Polaganje polaganje = polaganjeRepository.findById(p.getId()).orElseThrow(() -> new EntityNotFoundException("Polaganje not found"));
                PolaganjePredmetaHasPolaganje pphp = new PolaganjePredmetaHasPolaganje();
                pphp.setPolaganjePredmeta(polaganjePredmeta);
                pphp.setPolaganje(polaganje);

                polaganjePredmetaHasPolaganjeRepository.save(pphp);

                PohadjanjePredmetaHasPolaganja pphp2 = new PohadjanjePredmetaHasPolaganja();
                pphp.setPolaganje(polaganje);
                pphp2.setPohadjanjePredmeta(pohadjanjePredmeta);

                if(polaganjePredmetaDto.getKonacnaOcena() >= 6){
                    pohadjanjePredmetaHasPolaganjaRepository.save(pphp2);
                }
            }
        }

        PolaganjePredmetaDto dto = EntityDtoMapper.convertToDto(saved, PolaganjePredmetaDto.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    protected PolaganjePredmetaDto convertToDto(PolaganjePredmeta entity) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    protected PolaganjePredmeta convertToEntity(PolaganjePredmetaDto dto) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
