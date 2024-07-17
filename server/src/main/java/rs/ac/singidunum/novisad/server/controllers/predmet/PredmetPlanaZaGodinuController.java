package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.PlanZaGodinuDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetPlanaZaGodinuDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.predmet.PredmetPlanaZaGodinu;
import rs.ac.singidunum.novisad.server.services.predmet.PlanZaGodinuService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/api/predmet-plana-za-godinu")
public class PredmetPlanaZaGodinuController extends GenericController<PredmetPlanaZaGodinu, Long, PredmetPlanaZaGodinuDto> {

    private final PredmetService predmetService;
    private final PlanZaGodinuService planZaGodinuService;
    public PredmetPlanaZaGodinuController(GenericService<PredmetPlanaZaGodinu, Long> service, PredmetService predmetService, PlanZaGodinuService planZaGodinuService) {
        super(service);
        this.predmetService = predmetService;
        this.planZaGodinuService = planZaGodinuService;
    }

    @Override
    protected PredmetPlanaZaGodinuDto convertToDto(PredmetPlanaZaGodinu entity) throws IllegalAccessException, InstantiationException {
        PredmetDto predmetDto = EntityDtoMapper.convertToDto(entity.getPredmet(), PredmetDto.class);
        PlanZaGodinuDto planZaGodinuDto = EntityDtoMapper.convertToDto(entity.getPlanZaGodinu(), PlanZaGodinuDto.class);

        PredmetPlanaZaGodinuDto p = EntityDtoMapper.convertToDto(entity, PredmetPlanaZaGodinuDto.class);
        p.setPredmet(predmetDto);
        p.setPlanZaGodinu(planZaGodinuDto);

        return p;
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
