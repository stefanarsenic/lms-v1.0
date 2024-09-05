package rs.ac.singidunum.novisad.server.controllers.nastavnik;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.TipZvanjaDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipZvanja;

@RestController
@RequestMapping("/api/tip-zvanja")
@Secured({"ROLE_ADMIN","ROLE_SLUZBA","ROLE_NASTAVNIK"})
public class TipZvanjaController extends GenericController<TipZvanja, Long, TipZvanjaDto> {
    public TipZvanjaController(GenericService<TipZvanja, Long> service) {
        super(service);
    }

    @Override
    protected TipZvanjaDto convertToDto(TipZvanja entity) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(entity, TipZvanjaDto.class);
    }

    @Override
    protected TipZvanja convertToEntity(TipZvanjaDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, TipZvanja.class);
    }
}
