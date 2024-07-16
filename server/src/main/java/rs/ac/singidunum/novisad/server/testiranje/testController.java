package rs.ac.singidunum.novisad.server.testiranje;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;

import java.util.HashSet;

@Controller
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:4200")
public class testController extends GenericController<Nastavnik,Long, NastavnikDto> {
    public testController(GenericService<Nastavnik, Long> service) {
        super(service);
    }

    @Override
    @Secured({"ROLE_NASTAVNIK"})
    public ResponseEntity<Iterable<NastavnikDto>> findAll() throws IllegalAccessException, InstantiationException {
        return super.findAll();
    }

    @Override
    protected NastavnikDto convertToDto(Nastavnik entity) throws IllegalAccessException, InstantiationException {
        NastavnikDto nastavnikDto= EntityDtoMapper.convertToDto(entity,NastavnikDto.class);
        nastavnikDto.setZvanja(new HashSet<>());

        return nastavnikDto;
    }

    @Override
    protected Nastavnik convertToEntity(NastavnikDto dto) throws IllegalAccessException, InstantiationException {
        Nastavnik nastavnik=EntityDtoMapper.convertToEntity(dto,Nastavnik.class);

        nastavnik.setZvanja(new HashSet<>());

        return nastavnik;
    }
}
