package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.PlanZaGodinuDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetPlanaZaGodinuDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.predmet.PredmetPlanaZaGodinu;
import rs.ac.singidunum.novisad.server.services.fakultet.StudijskiProgramService;
import rs.ac.singidunum.novisad.server.services.predmet.PlanZaGodinuService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetPlanaZaGodinuService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/plan-za-godinu")
public class PlanZaGodinuController extends GenericController<PlanZaGodinu, Long, PlanZaGodinuDto> {

    private final PredmetPlanaZaGodinuService predmetPlanaZaGodinuService;
    private final PlanZaGodinuService planZaGodinuService;
    private final StudijskiProgramService studijskiProgramService;
    public PlanZaGodinuController(GenericService<PlanZaGodinu, Long> service, PredmetPlanaZaGodinuService predmetPlanaZaGodinuService, PlanZaGodinuService planZaGodinuService, StudijskiProgramService studijskiProgramService) {
        super(service);
        this.predmetPlanaZaGodinuService = predmetPlanaZaGodinuService;
        this.planZaGodinuService = planZaGodinuService;
        this.studijskiProgramService = studijskiProgramService;
    }

    @GetMapping("/by-studijski-program/{studijskiProgramId}")
    public ResponseEntity<?> getPlanoviZaGodinuByStudijskiProgram(@PathVariable Long studijskiProgramId){
        StudijskiProgram studijskiProgram = studijskiProgramService.findById(studijskiProgramId).orElseThrow();
        List<PlanZaGodinu> planoviZaGodinu = planZaGodinuService.findByStudijskiProgram(studijskiProgram);

        List<PlanZaGodinuDto> dtos = planoviZaGodinu.stream().map(planZaGodinu -> {
            try {
                return convertToDto(planZaGodinu);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/uslov-espb/{studijskiProgramId}/godina/{godina}")
    public ResponseEntity<Integer> getUslov(@PathVariable Long studijskiProgramId, @PathVariable Integer godina){
        Integer uslovEspb = planZaGodinuService.getPotrebnoEspbByStudijskiProgramIdAndGodina(studijskiProgramId, godina);

        return ResponseEntity.ok(uslovEspb);
    }

    @Override
    protected PlanZaGodinuDto convertToDto(PlanZaGodinu entity) throws IllegalAccessException, InstantiationException {
        Set<PredmetPlanaZaGodinu> predmetiPlanaZaGodinu = new HashSet<>(Collections.emptySet());

        if(entity.getPredmetiPlanaZaGodinu() != null) {
            for (PredmetPlanaZaGodinu predmetPlanaZaGodinu : entity.getPredmetiPlanaZaGodinu()) {
                Predmet predmet = new Predmet();
                predmet.setNaziv(predmetPlanaZaGodinu.getPredmet().getNaziv());

                PredmetPlanaZaGodinu p = new PredmetPlanaZaGodinu();
                p.setPredmet(predmet);

                predmetiPlanaZaGodinu.add(p);
            }
        }

        entity.setPredmetiPlanaZaGodinu(predmetiPlanaZaGodinu);

        return EntityDtoMapper.convertToDto(entity, PlanZaGodinuDto.class);
    }

    @Override
    protected PlanZaGodinu convertToEntity(PlanZaGodinuDto dto) throws IllegalAccessException, InstantiationException {
        Set<PredmetPlanaZaGodinu> predmetiPlanaZaGodinu = new HashSet<>(Collections.emptySet());

        if(dto.getPredmetiPlanaZaGodinu() != null){
            for(PredmetPlanaZaGodinuDto predmetPlanaZaGodinuDto : dto.getPredmetiPlanaZaGodinu()){
                predmetiPlanaZaGodinu.add(predmetPlanaZaGodinuService.findById(predmetPlanaZaGodinuDto.getId()).orElseThrow());
            }
        }

        PlanZaGodinu planZaGodinu = EntityDtoMapper.convertToEntity(dto, PlanZaGodinu.class);
        planZaGodinu.setPredmetiPlanaZaGodinu(predmetiPlanaZaGodinu);

        return planZaGodinu;
    }
}
