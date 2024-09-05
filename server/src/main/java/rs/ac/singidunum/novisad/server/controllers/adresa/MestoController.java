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
import rs.ac.singidunum.novisad.server.services.adresa.DrzavaService;

import java.util.Collections;

@RestController
@RequestMapping("/api/mesto")
@Secured({"ROLE_ADMIN","ROLE_SLUZBA","ROLE_NASTAVNIK","ROLE_STUDENT"})
public class MestoController extends GenericController<Mesto, Long, MestoDto> {

    private final DrzavaService drzavaService;
    public MestoController(GenericService<Mesto, Long> service, DrzavaService drzavaService) {
        super(service);
        this.drzavaService = drzavaService;
    }

    @Override
    protected MestoDto convertToDto(Mesto entity) throws IllegalAccessException, InstantiationException {
        entity.getDrzava().setMesta(Collections.emptySet());
        MestoDto mestoDto = EntityDtoMapper.convertToDto(entity, MestoDto.class);
        mestoDto.setDrzava(EntityDtoMapper.convertToDto(entity.getDrzava(), DrzavaDto.class));

        return mestoDto;
    }

    @Override
    protected Mesto convertToEntity(MestoDto dto) throws IllegalAccessException, InstantiationException {
        Drzava drzava = drzavaService.findById(dto.getDrzava().getId()).orElseThrow();
        dto.setDrzava(null);

        Mesto mesto = EntityDtoMapper.convertToEntity(dto, Mesto.class);
        mesto.setDrzava(drzava);

        return mesto;
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<MestoDto> create(MestoDto dto) throws IllegalAccessException, InstantiationException {
        return super.create(dto);
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<MestoDto> update(Long aLong, MestoDto dto) throws IllegalAccessException, InstantiationException {
        return super.update(aLong, dto);
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> delete(Long aLong) {
        return super.delete(aLong);
    }
}
