package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TipEvaluacijeDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipEvaluacije;

@RestController
@RequestMapping("/api/tip-evaluacije")
public class TipEvaluacijeController extends GenericController<TipEvaluacije, Long, TipEvaluacijeDto> {
    public TipEvaluacijeController(GenericService<TipEvaluacije, Long> service) {
        super(service);
    }

    @Override
    protected TipEvaluacijeDto convertToDto(TipEvaluacije entity) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(entity, TipEvaluacijeDto.class);
    }

    @Override
    protected TipEvaluacije convertToEntity(TipEvaluacijeDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(dto, TipEvaluacije.class);
    }
}
