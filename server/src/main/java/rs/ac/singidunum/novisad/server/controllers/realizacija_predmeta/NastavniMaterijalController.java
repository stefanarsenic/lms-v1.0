package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.NastavniMaterijalDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.NastavniMaterijal;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;
import rs.ac.singidunum.novisad.server.services.obavestenje.FajlService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/nastavni-materijal")
public class NastavniMaterijalController extends GenericController<NastavniMaterijal, Long, NastavniMaterijalDto> {

    private final NastavnikService nastavnikService;
    private final FajlService fajlService;
    public NastavniMaterijalController(GenericService<NastavniMaterijal, Long> service, NastavnikService nastavnikService, FajlService fajlService) {
        super(service);
        this.nastavnikService = nastavnikService;
        this.fajlService = fajlService;
    }

    @Override
    protected NastavniMaterijalDto convertToDto(NastavniMaterijal entity) throws IllegalAccessException, InstantiationException {
        NastavniMaterijalDto n = EntityDtoMapper.convertToDto(entity, NastavniMaterijalDto.class);

        Set<NastavnikDto> autoriDto = new HashSet<>(Collections.emptySet());

        if(entity.getAutori() != null){
            for(Nastavnik nastavnik : entity.getAutori()){
                autoriDto.add(EntityDtoMapper.convertToDto(nastavnik, NastavnikDto.class));
            }
        }

        n.setAutori(autoriDto);
        n.setFajl(EntityDtoMapper.convertToDto(entity.getFajl(), FajlDto.class));

        return n;
    }

    @Override
    protected NastavniMaterijal convertToEntity(NastavniMaterijalDto dto) throws IllegalAccessException, InstantiationException {
        NastavniMaterijal n = EntityDtoMapper.convertToEntity(dto, NastavniMaterijal.class);

        Set<Nastavnik> autori = new HashSet<>(Collections.emptySet());

        if(dto.getAutori() != null){
            for(NastavnikDto nastavnikDto : dto.getAutori()){
                autori.add(nastavnikService.findById(nastavnikDto.getId()).orElseThrow());
            }
        }

        Fajl fajl = (dto.getFajl().getId() != null) ? fajlService.findById(dto.getFajl().getId()).orElseThrow() : null;
        n.setFajl(fajl);

        return n;
    }
}
