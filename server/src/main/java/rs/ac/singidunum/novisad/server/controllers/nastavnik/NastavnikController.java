package rs.ac.singidunum.novisad.server.controllers.nastavnik;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.ZvanjeDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.nastavnik.Zvanje;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/nastavnik")
public class NastavnikController extends GenericController<Nastavnik, Long, NastavnikDto> {
    public NastavnikController(GenericService<Nastavnik, Long> service) {
        super(service);
    }

    @Override
    protected NastavnikDto convertToDto(Nastavnik entity) throws IllegalAccessException, InstantiationException {
        NastavnikDto nastavnikDto = EntityDtoMapper.convertToDto(entity, NastavnikDto.class);
        nastavnikDto.setZvanja(Collections.emptySet());

        Set<ZvanjeDto> zvanja = new HashSet<>(Collections.emptySet());

        if(entity.getZvanja() != null) {
            for (Zvanje zvanje : entity.getZvanja()) {
                zvanje.setNastavnik(null);
                zvanja.add(EntityDtoMapper.convertToDto(zvanje, ZvanjeDto.class));
            }
        }

        nastavnikDto.setZvanja(zvanja);

        return nastavnikDto;
    }

    @Override
    protected Nastavnik convertToEntity(NastavnikDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, Nastavnik.class);
    }
}
