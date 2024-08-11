package rs.ac.singidunum.novisad.server.controllers.obavestenja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;
import rs.ac.singidunum.novisad.server.dto.obavestenje.ObavestenjeDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.RealizacijaPredmetaDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;
import rs.ac.singidunum.novisad.server.repositories.obavestenje.ObavestenjeRepository;
import rs.ac.singidunum.novisad.server.services.obavestenje.ObavestenjeService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/obavestenja")
public class Obavestenja extends GenericController<Obavestenje,Long, ObavestenjeDto> {

    @Autowired
    ObavestenjeService obavestenjeService;

    public Obavestenja(ObavestenjeService service) {
        super(service);
        obavestenjeService=service;
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
        RealizacijaPredmetaDto realizacijaPredmetaDto=EntityDtoMapper.convertToDto(entity.getRealizacijaPredmeta(),RealizacijaPredmetaDto.class);
        realizacijaPredmetaDto.setObavestenja(new HashSet<>());
        obavestenjeDto.setRealizacijaPredmeta(realizacijaPredmetaDto);

        obavestenjeDto.setPrilozi(fajlDtos);

        return obavestenjeDto;

    }

    @PostMapping("/realizacija-predmeta")
    public ResponseEntity<Set<ObavestenjeDto>> getObavestenjaByPredmetIds(@RequestBody List<Long> predmetIds) throws IllegalAccessException, InstantiationException {

        Set<ObavestenjeDto> obavestenjeDtos=new HashSet<>();
        Set<Obavestenje> obavestenja = this.obavestenjeService.findObavestenjaByPredmetIds(predmetIds);
        for(Obavestenje obavestenje:obavestenja){
            obavestenjeDtos.add(convertToDto(obavestenje));
        }
        return ResponseEntity.ok(obavestenjeDtos);
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

        RealizacijaPredmeta realizacijaPredmeta=EntityDtoMapper.convertToDto(dto.getRealizacijaPredmeta(),RealizacijaPredmeta.class);
        obavestenje.setRealizacijaPredmeta(realizacijaPredmeta);
        obavestenje.setPrilozi(fajls);

        return obavestenje;
    }
}
