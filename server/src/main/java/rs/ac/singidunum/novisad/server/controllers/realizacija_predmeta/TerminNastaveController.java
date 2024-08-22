package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.TipNastaveDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TerminNastaveDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipNastave;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.TipNastaveService;
import rs.ac.singidunum.novisad.server.services.predmet.IshodService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.NastavniMaterijalService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.RealizacijaPredmetaService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.TerminNastaveService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/termin-nastave")
public class TerminNastaveController extends GenericController<TerminNastave, Long, TerminNastaveDto> {
    //TODO: security anotacije
    private final IshodService ishodService;
    private final TipNastaveService tipNastaveService;
    private final NastavniMaterijalService nastavniMaterijalService;
    private final TerminNastaveService terminNastaveService;
    private final RealizacijaPredmetaService realizacijaPredmetaService;
    public TerminNastaveController(GenericService<TerminNastave, Long> service, IshodService ishodService, TipNastaveService tipNastaveService, NastavniMaterijalService nastavniMaterijalService, TerminNastaveService terminNastaveService, RealizacijaPredmetaService realizacijaPredmetaService) {
        super(service);
        this.ishodService = ishodService;
        this.tipNastaveService = tipNastaveService;
        this.nastavniMaterijalService = nastavniMaterijalService;
        this.terminNastaveService = terminNastaveService;
        this.realizacijaPredmetaService = realizacijaPredmetaService;
    }

    @GetMapping("/predmet/{predmetId}")
    public ResponseEntity<List<TerminNastaveDto>> getAllByPredmet(@PathVariable Long predmetId) throws IllegalAccessException, InstantiationException {
        ArrayList<TerminNastaveDto> dtoList = new ArrayList<>();
        List<TerminNastave> original = terminNastaveService.findByPredmet(predmetId);
        for (TerminNastave entity : original) {
            TerminNastaveDto dto = convertToDto(entity);
            dtoList.add(dto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("/predmet/{predmetId}")
    public ResponseEntity<List<TerminNastaveDto>> create(@PathVariable Long predmetId, @RequestBody TerminNastaveDto dto) throws IllegalAccessException, InstantiationException {
        RealizacijaPredmeta realizacijaPredmeta = realizacijaPredmetaService.findByPredmetId(predmetId);
        TerminNastave entity = convertToEntity(dto);
        entity.setRealizacijaPredmeta(realizacijaPredmeta);
        List<TerminNastave> created = terminNastaveService.createRepeating(entity, dto.getEvent());
        List<TerminNastaveDto> dtos = created.stream().map(t -> {
            try {
                return convertToDto(t);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return new ResponseEntity<>(dtos, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminNastaveDto> update(@PathVariable Long id, @RequestBody TerminNastaveDto terminNastaveDto) throws IllegalAccessException, InstantiationException {
        TerminNastave terminNastave = terminNastaveService.findById(id).orElseThrow();

        terminNastave.setVremePocetka(terminNastaveDto.getVremePocetka());
        terminNastave.setVremeZavrsetka(terminNastaveDto.getVremeZavrsetka());

        TerminNastave saved = terminNastaveService.save(terminNastave);
        TerminNastaveDto dto = convertToDto(saved);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @Override
    protected TerminNastaveDto convertToDto(TerminNastave entity) throws IllegalAccessException, InstantiationException {
        TerminNastaveDto t = EntityDtoMapper.convertToDto(entity, TerminNastaveDto.class);
//        IshodDto ishodDto = EntityDtoMapper.convertToDto(entity.getIshod(), IshodDto.class);
        if(entity.getTipNastave() != null) {
            TipNastaveDto tipNastaveDto = EntityDtoMapper.convertToDto(entity.getTipNastave(), TipNastaveDto.class);
            t.setTipNastave(tipNastaveDto);
        }
//        NastavniMaterijalDto nastavniMaterijalDto = EntityDtoMapper.convertToDto(entity.getNastavniMaterijal(), NastavniMaterijalDto.class);
//        t.setIshod(ishodDto);
//        t.setNastavniMaterijal(nastavniMaterijalDto);

        return t;
    }

    @Override
    protected TerminNastave convertToEntity(TerminNastaveDto dto) throws IllegalAccessException, InstantiationException {
//        Ishod ishod = ishodService.findById(dto.getIshod().getId()).orElseThrow();
        TipNastave tipNastave = tipNastaveService.findById(dto.getTipNastave().getId()).orElseThrow();
//        NastavniMaterijal nastavniMaterijal = nastavniMaterijalService.findById(dto.getNastavniMaterijal().getId()).orElseThrow();

        TerminNastave t = EntityDtoMapper.convertToEntity(dto, TerminNastave.class);
//        t.setIshod(ishod);
        t.setTipNastave(tipNastave);
//        t.setNastavniMaterijal(nastavniMaterijal);

        return t;
    }
}
