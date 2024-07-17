package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;

@RestController
@RequestMapping("/api/ishod")
public class IshodController extends GenericController<Ishod, Long, IshodDto> {

    private final PredmetService predmetService;

    public IshodController(GenericService<Ishod, Long> service, PredmetService predmetService) {
        super(service);
        this.predmetService = predmetService;
    }

    @Override
    protected IshodDto convertToDto(Ishod entity) throws IllegalAccessException, InstantiationException {
        IshodDto ishodDto = EntityDtoMapper.convertToDto(entity, IshodDto.class);
        ishodDto.setPredmet(EntityDtoMapper.convertToDto(entity.getPredmet(), PredmetDto.class));
        ishodDto.getPredmet().setNastavnik(EntityDtoMapper.convertToDto(entity.getPredmet().getNastavnik(), NastavnikDto.class));
        ishodDto.getPredmet().setAsistent(EntityDtoMapper.convertToDto(entity.getPredmet().getAsistent(), NastavnikDto.class));

        return ishodDto;
    }

    @Override
    protected Ishod convertToEntity(IshodDto dto) throws IllegalAccessException, InstantiationException {
        Predmet predmet = predmetService.findById(dto.getPredmet().getId()).orElseThrow();

        Ishod ishod = EntityDtoMapper.convertToEntity(dto, Ishod.class);
        ishod.setPredmet(predmet);

        return ishod;
    }
}
