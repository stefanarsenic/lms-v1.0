package rs.ac.singidunum.novisad.server.controllers.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.student.AktivnostDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.student.Aktivnost;

@RestController
@RequestMapping("/api/aktivnost")
public class AktivnostController extends GenericController<Aktivnost, Long, AktivnostDto> {
    public AktivnostController(GenericService<Aktivnost, Long> service) {
        super(service);
    }

    @Override
    protected AktivnostDto convertToDto(Aktivnost entity) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(entity, AktivnostDto.class);
    }

    @Override
    protected Aktivnost convertToEntity(AktivnostDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, Aktivnost.class);
    }
}
