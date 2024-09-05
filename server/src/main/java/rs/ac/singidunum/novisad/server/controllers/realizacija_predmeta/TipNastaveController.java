package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.TipNastaveDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipNastave;

@RestController
@RequestMapping("/api/tip-nastave")
@Secured({"ROLE_SLUZBA","ROLE_ADMIN","ROLE_STUDENT","ROLE_NASTAVNIK"})
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

    @Override
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<TipNastaveDto> create(TipNastaveDto dto) throws IllegalAccessException, InstantiationException {
        return super.create(dto);
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<TipNastaveDto> update(Long aLong, TipNastaveDto dto) throws IllegalAccessException, InstantiationException {
        return super.update(aLong, dto);
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> delete(Long aLong) {
        return super.delete(aLong);
    }
}
