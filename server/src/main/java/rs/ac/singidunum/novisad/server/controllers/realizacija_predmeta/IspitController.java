package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.IspitDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.Ispit;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.IspitniRok;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.services.fakultet.StudijskiProgramService;
import rs.ac.singidunum.novisad.server.services.predmet.PlanZaGodinuService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.IspitService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.IspitniRokService;

import java.util.List;

@RestController
@RequestMapping("/api/ispit")
public class IspitController extends GenericController<Ispit, Long, IspitDto> {

    private final IspitService ispitService;
    private final PredmetService predmetService;
    private final StudijskiProgramService studijskiProgramService;
    private final IspitniRokService ispitniRokService;
    private final PlanZaGodinuService planZaGodinuService;

    public IspitController(GenericService<Ispit, Long> service, IspitService ispitService, PredmetService predmetService, StudijskiProgramService studijskiProgramService, IspitniRokService ispitniRokService, PlanZaGodinuService planZaGodinuService) {
        super(service);
        this.ispitService = ispitService;
        this.predmetService = predmetService;
        this.studijskiProgramService = studijskiProgramService;
        this.ispitniRokService = ispitniRokService;
        this.planZaGodinuService = planZaGodinuService;
    }

    @GetMapping("/ispitni-rok/{ispitniRokId}/studijski-program/{studijskiProgramId}")
    public ResponseEntity<List<IspitDto>> getAllByIspitniRokAndStudijskiProgram(@PathVariable Long ispitniRokId, @PathVariable Long studijskiProgramId){
        List<IspitDto> dtos = ispitService.findAllByIspitniRokIdAndStudijskiProgramId(ispitniRokId, studijskiProgramId).stream().map(ispit -> {
            try {
                return convertToDto(ispit);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createIspit(
        @RequestParam Long predmetId,
        @RequestParam Long ispitniRokId,
        @RequestParam Long studijskiProgramId,
        @RequestBody IspitDto ispitDto) throws IllegalAccessException, InstantiationException {

        try {
            Predmet predmet = predmetService.findById(predmetId).orElseThrow(() -> new RuntimeException("Predmet not found"));
            StudijskiProgram studijskiProgram = studijskiProgramService.findById(studijskiProgramId).orElseThrow(() -> new RuntimeException("Studijski program not found"));
            IspitniRok ispitniRok = ispitniRokService.findById(ispitniRokId).orElseThrow(() -> new RuntimeException("Ispitni rok not found"));

            Integer godinaPredmetaIspita = planZaGodinuService.getGodinaByPredmetAndStudijskiProgram(predmet, studijskiProgram);
            List<Ispit> postojeciIspiti = (List<Ispit>) ispitService.findAll();

            Ispit ispit = new Ispit();
            ispit.setKrajIspita(ispitDto.getKrajIspita());
            ispit.setPocetakIspita(ispitDto.getPocetakIspita());
            ispit.setPredmet(predmet);
            ispit.setStudijskiProgram(studijskiProgram);
            ispit.setIspitniRok(ispitniRok);

            if (ispit.getKrajIspita().isBefore(ispitniRok.getPocetak().atStartOfDay()) || ispit.getPocetakIspita().isAfter(ispitniRok.getKraj().plusDays(1).atStartOfDay())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Datumi ispita nisu u okviru ispitnog roka.");
            }

            if(!postojeciIspiti.isEmpty()) {
                for (Ispit i : postojeciIspiti) {
                    Integer godina = planZaGodinuService.getGodinaByPredmetAndStudijskiProgram(i.getPredmet(), i.getStudijskiProgram());
                    if (godinaPredmetaIspita.equals(godina) && ispit.getPocetakIspita().isAfter(i.getKrajIspita()) && ispit.getKrajIspita().isBefore(i.getPocetakIspita())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Ispiti predmeta koji se pohadjaju na istoj godini studijskog programa ne smeju da budu u istom danu.");
                    }
                    if(ispit.getPredmet().equals(i.getPredmet()) && ispit.getIspitniRok().equals(i.getIspitniRok()) && ispit.getStudijskiProgram().equals(i.getStudijskiProgram())){
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Ispit za ovaj predmet u ovom ispitnom roku vec postoji.");
                    }
                }
            }

            IspitDto dto = convertToDto(ispitService.save(ispit));
            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/datumi")
    public ResponseEntity<?> updatePocetakIKraj(@PathVariable Long id, @RequestBody IspitDto ispitDto) throws IllegalAccessException, InstantiationException {
        try {
            Ispit ispit = ispitService.findById(id).orElseThrow();
            ispit.setPocetakIspita(ispitDto.getPocetakIspita());
            ispit.setKrajIspita(ispitDto.getKrajIspita());

            Integer godinaPredmetaIspita = planZaGodinuService.getGodinaByPredmetAndStudijskiProgram(ispit.getPredmet(), ispit.getStudijskiProgram());
            List<Ispit> postojeciIspiti = (List<Ispit>) ispitService.findAll();

            if (ispit.getKrajIspita().isBefore(ispit.getIspitniRok().getPocetak().atStartOfDay()) || ispit.getPocetakIspita().isAfter(ispit.getIspitniRok().getKraj().plusDays(1).atStartOfDay())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Datumi ispita nisu u okviru ispitnog roka.");
            }

            if(!postojeciIspiti.isEmpty()) {
                for (Ispit i : postojeciIspiti) {
                    Integer godina = planZaGodinuService.getGodinaByPredmetAndStudijskiProgram(i.getPredmet(), i.getStudijskiProgram());
                    if (godinaPredmetaIspita.equals(godina) && ispit.getPocetakIspita().isAfter(i.getKrajIspita()) && ispit.getKrajIspita().isBefore(i.getPocetakIspita())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Ispiti predmeta koji se pohadjaju na istoj godini studijskog programa ne smeju da budu u istom danu.");
                    }
                }
            }

            IspitDto dto = convertToDto(ispitService.save(ispit));
            return ResponseEntity.accepted().body(dto);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    protected IspitDto convertToDto(Ispit entity) throws IllegalAccessException, InstantiationException {
        entity.getPredmet().setNastavnik(null); // prevencija beskonacnog json objekta
        entity.getPredmet().setAsistent(null);
        entity.getPredmet().setSilabus(null);
        entity.getStudijskiProgram().setFakultet(null);
        entity.getStudijskiProgram().setRukovodilac(null);
        entity.getStudijskiProgram().setStudentiNaGodini(null);
        return EntityDtoMapper.convertToDto(entity, IspitDto.class);
    }

    @Override
    protected Ispit convertToEntity(IspitDto dto) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
