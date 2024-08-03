package rs.ac.singidunum.novisad.server.controllers.obavestenja;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;
import rs.ac.singidunum.novisad.server.dto.obavestenje.ObavestenjeDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/obavestenja")
public class Obavestenja extends GenericController<Obavestenje,Long, ObavestenjeDto> {

    public Obavestenja(GenericService<Obavestenje, Long> service) {
        super(service);
    }

    @Override
    protected ObavestenjeDto convertToDto(Obavestenje entity) throws IllegalAccessException, InstantiationException {
        ObavestenjeDto obavestenjeDto= EntityDtoMapper.convertToDto(entity,ObavestenjeDto.class);
        obavestenjeDto.setPrilozi(new HashSet<>());
        Set<FajlDto> fajlDtos=new HashSet<>();

        for(Fajl fajl:entity.getPrilozi()){
            FajlDto fajlDto=EntityDtoMapper.convertToDto(fajl, FajlDto.class);
            fajlDto.setObavestenje(null);
            fajlDtos.add(fajlDto);
        }

        obavestenjeDto.setPrilozi(fajlDtos);

        return obavestenjeDto;

    }

    @Override
    protected Obavestenje convertToEntity(ObavestenjeDto dto) throws IllegalAccessException, InstantiationException {
        Obavestenje obavestenje= EntityDtoMapper.convertToEntity(dto,Obavestenje.class);
        obavestenje.setPrilozi(new HashSet<>());
        Set<Fajl> fajls=new HashSet<>();

        for(FajlDto fajlDto:dto.getPrilozi()){
            Fajl fajl=EntityDtoMapper.convertToEntity(fajlDto, Fajl.class);
            fajl.setObavestenje(null);
            fajls.add(fajl);
        }

        obavestenje.setPrilozi(fajls);

        return obavestenje;
    }
}
