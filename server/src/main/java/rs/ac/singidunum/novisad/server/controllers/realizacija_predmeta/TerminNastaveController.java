package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.TipNastaveDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.predmet.ObrazovniCiljDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.NastavniMaterijalDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TerminNastaveDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.*;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.model.predmet.ObrazovniCilj;
import rs.ac.singidunum.novisad.server.repositories.predmet.ObrazovniCiljRepository;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.TipNastaveService;
import rs.ac.singidunum.novisad.server.services.predmet.IshodService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.NastavniMaterijalService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.RealizacijaPredmetaService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.TerminNastaveService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/termin-nastave")
@Secured({"ROLE_NASTAVNIK","ROLE_SLUZBA","ROLE_ADMIN","ROLE_STUDENT"})
public class TerminNastaveController extends GenericController<TerminNastave, Long, TerminNastaveDto> {

    private final IshodService ishodService;
    private final TipNastaveService tipNastaveService;
    private final NastavniMaterijalService nastavniMaterijalService;
    private final TerminNastaveService terminNastaveService;
    private final RealizacijaPredmetaService realizacijaPredmetaService;
    private final PredmetRepository predmetRepository;
    private final ObrazovniCiljRepository obrazovniCiljRepository;

    public TerminNastaveController(GenericService<TerminNastave, Long> service, IshodService ishodService, TipNastaveService tipNastaveService, NastavniMaterijalService nastavniMaterijalService, TerminNastaveService terminNastaveService, RealizacijaPredmetaService realizacijaPredmetaService,
                                   PredmetRepository predmetRepository,
                                   ObrazovniCiljRepository obrazovniCiljRepository) {
        super(service);
        this.ishodService = ishodService;
        this.tipNastaveService = tipNastaveService;
        this.nastavniMaterijalService = nastavniMaterijalService;
        this.terminNastaveService = terminNastaveService;
        this.realizacijaPredmetaService = realizacijaPredmetaService;
        this.predmetRepository = predmetRepository;
        this.obrazovniCiljRepository = obrazovniCiljRepository;
    }

    @GetMapping("/by-nastavnik-and-predmet")
    public ResponseEntity<List<TerminNastaveDto>> getAllByNastavnikAndPredmet(
        @PathParam("nastavnikId") Long nastavnikId,
        @PathParam("predmetId") Long predmetId
    ) throws IllegalAccessException, InstantiationException {

        List<TerminNastave> termini = terminNastaveService.findTerminiNastaveByNastavnikAndPredmet(nastavnikId, predmetId);
        List<TerminNastaveDto> dtos = ListToDto(termini);

        return ResponseEntity.ok(dtos);
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
    @Secured({"ROLE_SLUZBA","ROLE_ADMIN"})
    public ResponseEntity<TerminNastaveDto> create(@PathVariable Long predmetId, @RequestBody TerminNastaveDto dto) throws IllegalAccessException, InstantiationException {
        RealizacijaPredmeta realizacijaPredmeta = realizacijaPredmetaService.findByPredmetId(predmetId);
        TerminNastave entity = convertToEntity(dto);
        entity.setRealizacijaPredmeta(realizacijaPredmeta);
        TerminNastave created = terminNastaveService.create(entity);
        TerminNastaveDto terminNastaveDto = convertToDto(created);

        return new ResponseEntity<>(terminNastaveDto, HttpStatus.CREATED);
    }

    @PostMapping("/predmet/{predmetId}/recurring")
    @Secured({"ROLE_SLUZBA","ROLE_ADMIN"})
    public ResponseEntity<List<TerminNastaveDto>> createRecurring(@PathVariable Long predmetId, @RequestBody TerminNastaveDto dto) throws IllegalAccessException, InstantiationException {
        RealizacijaPredmeta realizacijaPredmeta = realizacijaPredmetaService.findByPredmetId(predmetId);
        TerminNastave entity = convertToEntity(dto);
        entity.setRealizacijaPredmeta(realizacijaPredmeta);
        List<TerminNastave> created = terminNastaveService.createRecurring(entity, dto.getEvent());
        List<TerminNastaveDto> dtos = created.stream().map(t -> {
            try {
                return convertToDto(t);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return new ResponseEntity<>(dtos, HttpStatus.CREATED);
    }

    @PutMapping("/{terminNastaveId}/ishod")
    @Secured({"ROLE_SLUZBA","ROLE_ADMIN"})
    public ResponseEntity<?> updateIshod(
            @PathVariable Long terminNastaveId,
            @PathParam("predmetId") Long predmetId,
            @PathParam("nastavniMaterijalId") Long nastavniMaterijalId,
            @RequestBody IshodDto ishodDto) throws IllegalAccessException, InstantiationException {

        TerminNastave terminNastave = terminNastaveService.findById(terminNastaveId).orElseThrow(() -> new EntityNotFoundException("Termin nastave not found with id: " + terminNastaveId.toString()));
        Predmet predmet = predmetRepository.findById(predmetId).orElseThrow(() -> new EntityNotFoundException("Predmet not found with id: " + predmetId.toString()));
        NastavniMaterijal nastavniMaterijal = new NastavniMaterijal();

        Optional<Ishod> i = ishodService.findById(ishodDto.getId());
        if(i.isPresent()){
            Ishod newIshod = new Ishod();
            newIshod.setId(i.get().getId());
            newIshod.setOpis(ishodDto.getOpis());
            if(ishodDto.getNastavniMaterijal() != null) {
                nastavniMaterijal = nastavniMaterijalService.findById(ishodDto.getNastavniMaterijal().getId()).orElseThrow(() -> new EntityNotFoundException("Nastavni materijal not found with id: " + nastavniMaterijalId.toString()));
                newIshod.setNastavniMaterijal(nastavniMaterijal);
            }
            if(!ishodDto.getObrazovniCiljevi().isEmpty()){
                obrazovniCiljRepository.deleteAllByIshodId(i.get().getId());

                for(ObrazovniCiljDto o : ishodDto.getObrazovniCiljevi()){
                    ObrazovniCilj obrazovniCilj = new ObrazovniCilj();
                    obrazovniCilj.setId(o.getId());
                    obrazovniCilj.setOpis(o.getOpis());
                    obrazovniCilj.setIshod(newIshod);

                    obrazovniCiljRepository.save(obrazovniCilj);
                }
            }

            Ishod saved = ishodService.save(newIshod);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        Ishod ishod = new Ishod();

        if(ishodDto.getNastavniMaterijal() != null) {
            nastavniMaterijal = nastavniMaterijalService.findById(ishodDto.getNastavniMaterijal().getId()).orElseThrow(() -> new EntityNotFoundException("Nastavni materijal not found with id: " + nastavniMaterijalId.toString()));
            ishod.setNastavniMaterijal(nastavniMaterijal);
        }

        ishod.setOpis(ishodDto.getOpis());
        ishod.setPredmet(predmet);
        Ishod savedIshod = ishodService.save(ishod);


        if(!ishodDto.getObrazovniCiljevi().isEmpty()){
            for(ObrazovniCiljDto o : ishodDto.getObrazovniCiljevi()){
                ObrazovniCilj obrazovniCilj = new ObrazovniCilj();
                obrazovniCilj.setOpis(o.getOpis());
                obrazovniCilj.setIshod(savedIshod);

                obrazovniCiljRepository.save(obrazovniCilj);
            }
        }

        terminNastave.setIshod(savedIshod);
        TerminNastave saved = terminNastaveService.save(terminNastave);
        TerminNastaveDto dto = convertToDto(saved);

        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_SLUZBA","ROLE_ADMIN"})
    public ResponseEntity<TerminNastaveDto> update(@PathVariable Long id, @RequestBody TerminNastaveDto terminNastaveDto) throws IllegalAccessException, InstantiationException {
        TerminNastave terminNastave = terminNastaveService.findById(id).orElseThrow();

        terminNastave.setVremePocetka(terminNastaveDto.getVremePocetka());
        terminNastave.setVremeZavrsetka(terminNastaveDto.getVremeZavrsetka());

        TerminNastave saved = terminNastaveService.save(terminNastave);
        TerminNastaveDto dto = convertToDto(saved);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-all-by-realizacija-predmeta/{terminNastaveId}")
    @Secured({"ROLE_SLUZBA","ROLE_ADMIN"})
    public ResponseEntity<?> deleteAllByRealizacijaPredmeta(@PathVariable Long terminNastaveId){
        TerminNastave terminNastave = terminNastaveService.findById(terminNastaveId).orElseThrow(
                () -> new EntityNotFoundException("Termin nastave not found with id: " + terminNastaveId.toString())
        );

        RealizacijaPredmeta realizacijaPredmeta = terminNastave.getRealizacijaPredmeta();
        if(realizacijaPredmeta != null){
            terminNastaveService.deleteAllByRealizacijaPredmetaId(realizacijaPredmeta.getId());
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Realizacija predmeta for termin nastave with id: " + terminNastaveId.toString() + " doesn't exist");
        }
    }

    @Override
    protected TerminNastaveDto convertToDto(TerminNastave entity) throws IllegalAccessException, InstantiationException {
        TerminNastaveDto t = EntityDtoMapper.convertToDto(entity, TerminNastaveDto.class);
        if(entity.getIshod() != null){
            IshodDto ishodDto = EntityDtoMapper.convertToDto(entity.getIshod(), IshodDto.class);
            List<ObrazovniCiljDto> dtos = new ArrayList<>();
            if(!entity.getIshod().getObrazovniCiljevi().isEmpty()) {
                for (ObrazovniCilj o : entity.getIshod().getObrazovniCiljevi()) {
                    o.setIshod(null);
                    dtos.add(EntityDtoMapper.convertToDto(o, ObrazovniCiljDto.class));
                }

                ishodDto.setObrazovniCiljevi(dtos);
                t.setIshod(ishodDto);
            }

            if(entity.getIshod().getNastavniMaterijal() != null) {
                NastavniMaterijalDto nastavniMaterijalDto = EntityDtoMapper.convertToDto(entity.getIshod().getNastavniMaterijal(), NastavniMaterijalDto.class);
                t.setNastavniMaterijal(nastavniMaterijalDto);
            }

        }
        if(entity.getTipNastave() != null) {
            TipNastaveDto tipNastaveDto = EntityDtoMapper.convertToDto(entity.getTipNastave(), TipNastaveDto.class);
            t.setTipNastave(tipNastaveDto);
        }

        return t;
    }

    private List<TerminNastaveDto> ListToDto(List<TerminNastave> entities) throws IllegalAccessException, InstantiationException {
        List<TerminNastaveDto> dtoList = new ArrayList<>();

        for (TerminNastave entity : entities) {
            TerminNastaveDto dto = convertToDto(entity);
            dtoList.add(dto);
        }

        return dtoList;
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
