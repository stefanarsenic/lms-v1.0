package rs.ac.singidunum.novisad.server.controllers.obavestenja;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;
import rs.ac.singidunum.novisad.server.dto.obavestenje.ObavestenjeDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.RealizacijaPredmetaDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.repositories.obavestenje.ObavestenjeRepository;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.services.obavestenje.ObavestenjeService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/obavestenja")
public class ObavestenjaController extends GenericController<Obavestenje,Long, ObavestenjeDto> {

    @Autowired
    ObavestenjeService obavestenjeService;
    private final PredmetRepository predmetRepository;
    private final ObavestenjeRepository obavestenjeRepository;

    public ObavestenjaController(ObavestenjeService service,
                                 PredmetRepository predmetRepository,
                                 ObavestenjeRepository obavestenjeRepository) {
        super(service);
        obavestenjeService=service;
        this.predmetRepository = predmetRepository;
        this.obavestenjeRepository = obavestenjeRepository;
    }

    @GetMapping("/by-predmet")
    public ResponseEntity<?> getObavestenjaByPredmet(@PathParam("predmetId") Long predmetId){
        Predmet predmet = predmetRepository.findById(predmetId).orElseThrow(() -> new EntityNotFoundException("Predmet not found with id: " + predmetId.toString()));

        List<Obavestenje> obavestenjeList = obavestenjeRepository.findObavestenjeByPredmetId(predmet.getId());
        List<ObavestenjeDto> dtos = obavestenjeList.stream().map(obavestenje -> {
            try {
                return EntityDtoMapper.convertToDto(obavestenje, ObavestenjeDto.class);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return ResponseEntity.ok(dtos);
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

    @GetMapping("/predmet/{predmetId}")
    public ResponseEntity<Set<ObavestenjeDto>> getObavestenjaByPredmetId(@PathVariable Long predmetId) throws IllegalAccessException, InstantiationException {

        Set<ObavestenjeDto> obavestenjeDtos=new HashSet<>();
        Set<Obavestenje> obavestenja = this.obavestenjeService.findObavestenjaByPredmetId(predmetId);
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
