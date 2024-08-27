package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.EvaluacijaZnanjaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TerminNastaveDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TipEvaluacijeDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipEvaluacije;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.EvaluacijaZnanjaRepository;
import rs.ac.singidunum.novisad.server.services.obavestenje.FajlService;
import rs.ac.singidunum.novisad.server.services.predmet.IshodService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.EvaluacijaZnanjaService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.RealizacijaPredmetaService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.TipEvaluacijeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/evaluacija-znanja")
public class EvaluacijaZnanjaController extends GenericController<EvaluacijaZnanja, Long, EvaluacijaZnanjaDto> {

    private final EvaluacijaZnanjaService evaluacijaZnanjaService;
    private final RealizacijaPredmetaService realizacijaPredmetaService;
    private final IshodService ishodService;
    private final FajlService fajlService;
    private final TipEvaluacijeService tipEvaluacijeService;
    private final EvaluacijaZnanjaRepository evaluacijaZnanjaRepository;

    public EvaluacijaZnanjaController(GenericService<EvaluacijaZnanja, Long> service, EvaluacijaZnanjaService evaluacijaZnanjaService, RealizacijaPredmetaService realizacijaPredmetaService, IshodService ishodService, FajlService fajlService, TipEvaluacijeService tipEvaluacijeService,
                                      EvaluacijaZnanjaRepository evaluacijaZnanjaRepository) {
        super(service);
        this.evaluacijaZnanjaService = evaluacijaZnanjaService;
        this.realizacijaPredmetaService = realizacijaPredmetaService;
        this.ishodService = ishodService;
        this.fajlService = fajlService;
        this.tipEvaluacijeService = tipEvaluacijeService;
        this.evaluacijaZnanjaRepository = evaluacijaZnanjaRepository;
    }

    @GetMapping("/predmet/{predmetId}")
    public ResponseEntity<List<EvaluacijaZnanjaDto>> getAllByPredmet(@PathVariable Long predmetId) throws IllegalAccessException, InstantiationException {
        ArrayList<EvaluacijaZnanjaDto> dtoList = new ArrayList<>();
        List<EvaluacijaZnanja> original = evaluacijaZnanjaService.findEvaluacijaZnanjaByPredmetId(predmetId);
        for (EvaluacijaZnanja entity : original) {
            EvaluacijaZnanjaDto dto = convertToDto(entity);
            dtoList.add(dto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("/predmet/{predmetId}")
    public ResponseEntity<EvaluacijaZnanjaDto> create(
            @RequestParam String fajlSifra,
            @PathVariable Long predmetId,
            @RequestBody EvaluacijaZnanjaDto dto) throws IllegalAccessException, InstantiationException {
        RealizacijaPredmeta realizacijaPredmeta = realizacijaPredmetaService.findByPredmetId(predmetId);
        TipEvaluacije tipEvaluacije = tipEvaluacijeService.findById(dto.getTipEvaluacije().getId()).orElseThrow();
        Fajl fajl = fajlService.findBySifra(fajlSifra);

        EvaluacijaZnanja entity = convertToEntity(dto);
        entity.setRealizacijaPredmeta(realizacijaPredmeta);
        entity.setTipEvaluacije(tipEvaluacije);
        entity.setInstrumentEvaluacije(fajl);

        Ishod ishod = new Ishod();
        ishod.setOpis(dto.getIshod().getOpis());
        ishod.setPredmet(realizacijaPredmeta.getPredmet());
        Ishod createdIshod = ishodService.save(ishod);

        entity.setIshod(createdIshod);

        EvaluacijaZnanja created = evaluacijaZnanjaRepository.save(entity);
        EvaluacijaZnanjaDto evaluacijaZnanjaDto = convertToDto(created);

        return new ResponseEntity<>(evaluacijaZnanjaDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacijaZnanjaDto> update(@PathVariable Long id, @RequestBody EvaluacijaZnanjaDto evaluacijaZnanjaDto) throws IllegalAccessException, InstantiationException {
        EvaluacijaZnanja evaluacijaZnanja = evaluacijaZnanjaService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Evaluacija znanja not found with id: " + id.toString())
        );

        evaluacijaZnanja.setVremePocetka(evaluacijaZnanjaDto.getVremePocetka());
        evaluacijaZnanja.setVremeZavrsetka(evaluacijaZnanjaDto.getVremeZavrsetka());

        EvaluacijaZnanja saved = evaluacijaZnanjaService.save(evaluacijaZnanja);
        EvaluacijaZnanjaDto dto = convertToDto(saved);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @Override
    protected EvaluacijaZnanjaDto convertToDto(EvaluacijaZnanja entity) throws IllegalAccessException, InstantiationException {
        EvaluacijaZnanjaDto dto = EntityDtoMapper.convertToDto(entity, EvaluacijaZnanjaDto.class);
        if(entity.getIshod() != null) {
            Ishod ishod = ishodService.findById(entity.getIshod().getId()).orElseThrow(
                    () -> new EntityNotFoundException("Ishod not found with id: " + entity.getIshod().getId().toString())
            );
            dto.setIshod(EntityDtoMapper.convertToDto(ishod, IshodDto.class));
        }
        if(entity.getInstrumentEvaluacije() != null){
            Fajl fajl = fajlService.findById(entity.getInstrumentEvaluacije().getId()).orElseThrow(
                    () -> new EntityNotFoundException("Instrument evaluacije not found with id: " + entity.getInstrumentEvaluacije().getId().toString())
            );
            dto.setInstrumentEvaluacije(EntityDtoMapper.convertToDto(fajl, FajlDto.class));
        }
        if(entity.getTipEvaluacije() != null){
            TipEvaluacije tipEvaluacije = tipEvaluacijeService.findById(entity.getTipEvaluacije().getId()).orElseThrow(
                    () -> new EntityNotFoundException("Tip evaluacije not found with id: " + entity.getTipEvaluacije().getId().toString())
            );
            dto.setTipEvaluacije(EntityDtoMapper.convertToDto(tipEvaluacije, TipEvaluacijeDto.class));
        }

        return dto;
    }

    @Override
    protected EvaluacijaZnanja convertToEntity(EvaluacijaZnanjaDto dto) throws IllegalAccessException, InstantiationException {

        return EntityDtoMapper.convertToEntity(dto, EvaluacijaZnanja.class);
    }
}
