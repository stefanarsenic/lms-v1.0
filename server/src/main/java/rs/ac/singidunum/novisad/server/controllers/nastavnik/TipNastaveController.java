package rs.ac.singidunum.novisad.server.controllers.nastavnik;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.TipNastaveDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipNastave;

@RestController
@RequestMapping("/api/tip-nastave")
public class TipNastaveController extends GenericController<TipNastave, Long, TipNastaveDto> {
    public TipNastaveController(GenericService<TipNastave, Long> service) {
        super(service);
    }

    @Override
    protected TipNastaveDto convertToDto(TipNastave entity) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(entity, TipNastaveDto.class);
    }

    @Override
    protected TipNastave convertToEntity(TipNastaveDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, TipNastave.class);
    }
}
