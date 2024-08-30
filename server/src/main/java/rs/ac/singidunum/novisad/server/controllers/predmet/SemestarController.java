package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.SemestarDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Semestar;

@RestController
@RequestMapping("/api/semestar")
public class SemestarController extends GenericController<Semestar, Long, SemestarDto> {
    public SemestarController(GenericService<Semestar, Long> service) {
        super(service);
    }

    @Override
    protected SemestarDto convertToDto(Semestar entity) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(entity, SemestarDto.class);
    }

    @Override
    protected Semestar convertToEntity(SemestarDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, Semestar.class);
    }
}
