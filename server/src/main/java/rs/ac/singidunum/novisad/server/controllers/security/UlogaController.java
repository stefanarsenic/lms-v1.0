package rs.ac.singidunum.novisad.server.controllers.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.UlogaDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.Uloga;
import rs.ac.singidunum.novisad.server.services.secuirty.UlogaService;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/uloge")
@RestController
public class UlogaController extends GenericController<Uloga,Long, UlogaDto> {
    public UlogaController(GenericService<Uloga,Long> service) {
        super(service);
    }

    @Autowired
    UlogaService ulogaService;
    @Override
    protected UlogaDto convertToDto(Uloga entity) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(entity,UlogaDto.class);
    }

    @Override
    protected Uloga convertToEntity(UlogaDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto,Uloga.class);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUsers(@RequestBody List<Long> rolesIds) {
        ulogaService.deleteRoles(rolesIds);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/dodaj")
    public ResponseEntity<UlogaDto> create(UlogaDto dto) throws IllegalAccessException, InstantiationException {
        return super.create(dto);
    }
}
