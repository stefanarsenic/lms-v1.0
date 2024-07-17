package rs.ac.singidunum.novisad.server.controllers.nastavnik;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NaucnaOblastDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.NaucnaOblast;

@RestController
@RequestMapping("/api/naucna-oblast")
public class NaucnaOblastController extends GenericController<NaucnaOblast, Long, NaucnaOblastDto> {
    public NaucnaOblastController(GenericService<NaucnaOblast, Long> service) {
        super(service);
    }

    @Override
    protected NaucnaOblastDto convertToDto(NaucnaOblast entity) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(entity, NaucnaOblastDto.class);
    }

    @Override
    protected NaucnaOblast convertToEntity(NaucnaOblastDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, NaucnaOblast.class);
    }
}
