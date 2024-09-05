package rs.ac.singidunum.novisad.server.controllers.adresa;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.adresa.DrzavaDto;
import rs.ac.singidunum.novisad.server.dto.adresa.MestoDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Drzava;
import rs.ac.singidunum.novisad.server.model.adresa.Mesto;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/drzava")
@Secured({"ROLE_ADMIN","ROLE_SLUZBA","ROLE_NASTAVNIK","ROLE_STUDENT"})
public class DrzavaController extends GenericController<Drzava, Long, DrzavaDto> {
    public DrzavaController(GenericService<Drzava, Long> service) {
        super(service);
    }

    @Override
    protected DrzavaDto convertToDto(Drzava entity) throws IllegalAccessException, InstantiationException {
        DrzavaDto drzavaDto = EntityDtoMapper.convertToDto(entity, DrzavaDto.class);
        drzavaDto.setMesta(Collections.emptySet());

        Set<MestoDto> mesta = new java.util.HashSet<>(Collections.emptySet());

        if(entity.getMesta() != null) {
            for (Mesto mesto : entity.getMesta()) {
                mesto.setDrzava(null);
                mesta.add(EntityDtoMapper.convertToDto(mesto, MestoDto.class));
            }
        }
        drzavaDto.setMesta(mesta);

        return drzavaDto;
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<DrzavaDto> create(DrzavaDto dto) throws IllegalAccessException, InstantiationException {
        return super.create(dto);
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> delete(Long aLong) {
        return super.delete(aLong);
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<DrzavaDto> update(Long aLong, DrzavaDto dto) throws IllegalAccessException, InstantiationException {
        return super.update(aLong, dto);
    }

    @Override
    protected Drzava convertToEntity(DrzavaDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, Drzava.class);
    }
}
