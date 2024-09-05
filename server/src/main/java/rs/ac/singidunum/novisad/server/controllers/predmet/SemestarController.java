package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.SemestarDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Semestar;

@RestController
@RequestMapping("/api/semestar")
@Secured({"ROLE_SLUZBA","ROLE_ADMIN","ROLE_STUDENT","ROLE_NASTAVNIK"})
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

    @Override
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK"})
    public ResponseEntity<SemestarDto> create(SemestarDto dto) throws IllegalAccessException, InstantiationException {
        return super.create(dto);
    }

    @Override
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK"})
    public ResponseEntity<SemestarDto> update(Long aLong, SemestarDto dto) throws IllegalAccessException, InstantiationException {
        return super.update(aLong, dto);
    }

    @Override
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK"})
    public ResponseEntity<Void> delete(Long aLong) {
        return super.delete(aLong);
    }
}
