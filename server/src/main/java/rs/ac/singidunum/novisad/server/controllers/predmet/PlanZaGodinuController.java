package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.PlanZaGodinuDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetPlanaZaGodinuDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.predmet.PredmetPlanaZaGodinu;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetPlanaZaGodinuService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/plan-za-godinu")
public class PlanZaGodinuController extends GenericController<PlanZaGodinu, Long, PlanZaGodinuDto> {

    private final PredmetPlanaZaGodinuService predmetPlanaZaGodinuService;
    public PlanZaGodinuController(GenericService<PlanZaGodinu, Long> service, PredmetPlanaZaGodinuService predmetPlanaZaGodinuService) {
        super(service);
        this.predmetPlanaZaGodinuService = predmetPlanaZaGodinuService;
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
