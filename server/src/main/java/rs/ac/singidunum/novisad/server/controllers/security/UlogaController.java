package rs.ac.singidunum.novisad.server.controllers.security;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.UlogaDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.Uloga;


@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/uloge")
@RestController
public class UlogaController extends GenericController<Uloga,Long, UlogaDto> {
    public UlogaController(GenericService<Uloga,Long> service) {
        super(service);
    }

    @Override
    protected UlogaDto convertToDto(Uloga entity) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(entity,UlogaDto.class);
    }

    @Override
    protected Uloga convertToEntity(UlogaDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto,Uloga.class);
    }
}
