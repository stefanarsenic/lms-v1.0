package rs.ac.singidunum.novisad.server.controllers.predmet;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PlanZaGodinuDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetPlanaZaGodinuDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.predmet.PredmetPlanaZaGodinu;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetPlanaZaGodinuRepository;
import rs.ac.singidunum.novisad.server.services.fakultet.StudijskiProgramService;
import rs.ac.singidunum.novisad.server.services.predmet.PlanZaGodinuService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetPlanaZaGodinuService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/predmet-plana-za-godinu")
@Secured({"ROLE_SLUZBA","ROLE_ADMIN","ROLE_STUDENT","ROLE_NASTAVNIK"})
public class PredmetPlanaZaGodinuController extends GenericController<PredmetPlanaZaGodinu, Long, PredmetPlanaZaGodinuDto> {

    private final PredmetService predmetService;
    private final PlanZaGodinuService planZaGodinuService;
    private final PredmetPlanaZaGodinuService predmetPlanaZaGodinuService;
    private final PredmetPlanaZaGodinuRepository predmetPlanaZaGodinuRepository;
    private final StudijskiProgramService studijskiProgramService;

    public PredmetPlanaZaGodinuController(GenericService<PredmetPlanaZaGodinu, Long> service, PredmetService predmetService, PlanZaGodinuService planZaGodinuService, PredmetPlanaZaGodinuService predmetPlanaZaGodinuService,
                                          PredmetPlanaZaGodinuRepository predmetPlanaZaGodinuRepository, StudijskiProgramService studijskiProgramService) {
        super(service);
        this.predmetService = predmetService;
        this.planZaGodinuService = planZaGodinuService;
        this.predmetPlanaZaGodinuService = predmetPlanaZaGodinuService;
        this.predmetPlanaZaGodinuRepository = predmetPlanaZaGodinuRepository;
        this.studijskiProgramService = studijskiProgramService;
    }

    @GetMapping("/by-plan-za-godinu/{planZaGodinuId}")
    public ResponseEntity<?> getPredmetiPlanaZaGodinuByPlanZaGodinu(@PathVariable Long planZaGodinuId){
        PlanZaGodinu planZaGodinu = planZaGodinuService.findById(planZaGodinuId).orElseThrow(
                () -> new EntityNotFoundException("PlanZaGodinu not found with id:" + planZaGodinuId)
        );
        List<PredmetPlanaZaGodinu> lista = predmetPlanaZaGodinuService.findPredmetPlanaZaGodinusByPlanZaGodinu(planZaGodinu);

        List<PredmetPlanaZaGodinuDto> dtos = lista.stream().map(predmetPlanaZaGodinu -> {
            try {
                return convertToDto(predmetPlanaZaGodinu);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/in-batch/{planZaGodinuId}")
    @Secured({"ROLE_ADMIN","ROLE_SLUZBA"})
    public ResponseEntity<?> createInBatch(@PathVariable Long planZaGodinuId, @RequestBody List<PredmetPlanaZaGodinuDto> dtos) {

        PlanZaGodinu planZaGodinu = planZaGodinuService.findById(planZaGodinuId).orElseThrow(
                () -> new EntityNotFoundException("PlanZaGodinu not found with id:" + planZaGodinuId)
        );
        StudijskiProgram studijskiProgram = studijskiProgramService.findById(planZaGodinu.getStudijskiProgram().getId()).orElseThrow(
                () -> new EntityNotFoundException("Studijski program not found with id:" + planZaGodinu.getStudijskiProgram().getId())
        );

        if(dtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Dostavljena lista ne sme biti prazna");
        }

        predmetPlanaZaGodinuRepository.deleteAllByPlanZaGodinu(planZaGodinu);
        List<Predmet> predmetiStudijskogPrograma = predmetService.findPredmetiByStudijskiProgram(studijskiProgram.getId());
        List<PredmetPlanaZaGodinuDto> savesDto = new ArrayList<>();

        for (PredmetPlanaZaGodinuDto dto : dtos) {
            Optional<PredmetPlanaZaGodinu> ppzg = predmetPlanaZaGodinuService.findById(dto.getId());
            if (ppzg.isEmpty()) {
                Predmet predmet = predmetService.findById(dto.getPredmet().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Predmet not found with id: " + dto.getPredmet().getId()));

                if(predmetiStudijskogPrograma.contains(predmet)){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Predmet je vec dodat u plan studijskog programa");
                }
                PredmetPlanaZaGodinu newPpzg = new PredmetPlanaZaGodinu();
                newPpzg.setPredmet(predmet);
                newPpzg.setPlanZaGodinu(planZaGodinu);
                newPpzg.setSemestar(dto.getSemestar());

                try {
                    PredmetPlanaZaGodinu saved = predmetPlanaZaGodinuService.save(newPpzg);
                    savesDto.add(convertToDto(saved));
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return new ResponseEntity<>(savesDto, HttpStatus.CREATED);
    }

    @Override
    protected PredmetPlanaZaGodinuDto convertToDto(PredmetPlanaZaGodinu entity) throws IllegalAccessException, InstantiationException {
        PredmetDto predmetDto = EntityDtoMapper.convertToDto(entity.getPredmet(), PredmetDto.class);
        predmetDto.setSilabus(null);
        PlanZaGodinuDto planZaGodinuDto = EntityDtoMapper.convertToDto(entity.getPlanZaGodinu(), PlanZaGodinuDto.class);
        planZaGodinuDto.setPredmetiPlanaZaGodinu(null);

        PredmetPlanaZaGodinuDto p = EntityDtoMapper.convertToDto(entity, PredmetPlanaZaGodinuDto.class);
        p.setPredmet(predmetDto);
        p.setPlanZaGodinu(planZaGodinuDto);

        return p;
    }

    @Override
    @Secured({"ROLE_ADMIN","ROLE_SLUZBA"})
    public ResponseEntity<Void> delete(Long aLong) {
        return super.delete(aLong);
    }

    @Override
    @Secured({"ROLE_ADMIN","ROLE_SLUZBA"})
    public ResponseEntity<PredmetPlanaZaGodinuDto> update(Long aLong, PredmetPlanaZaGodinuDto dto) throws IllegalAccessException, InstantiationException {
        return super.update(aLong, dto);
    }

    @Override
    protected PredmetPlanaZaGodinu convertToEntity(PredmetPlanaZaGodinuDto dto) throws IllegalAccessException, InstantiationException {
        Predmet predmet = predmetService.findById(dto.getPredmet().getId()).orElseThrow();
        PlanZaGodinu planZaGodinu = planZaGodinuService.findById(dto.getPlanZaGodinu().getId()).orElseThrow();

        PredmetPlanaZaGodinu p = EntityDtoMapper.convertToEntity(dto, PredmetPlanaZaGodinu.class);
        p.setPredmet(predmet);
        p.setPlanZaGodinu(planZaGodinu);

        return p;
    }
}
