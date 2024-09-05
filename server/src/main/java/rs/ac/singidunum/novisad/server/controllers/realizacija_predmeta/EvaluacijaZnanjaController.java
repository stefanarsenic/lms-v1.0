package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.EvaluacijaZnanjaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.RealizacijaPredmetaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TerminNastaveDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TipEvaluacijeDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.*;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.model.predmet.ObrazovniCilj;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.repositories.nastavnik.NastavnikRepository;
import rs.ac.singidunum.novisad.server.repositories.obavestenje.FajlRepository;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.EvaluacijaZnanjaRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.TipPolaganjaRepository;
import rs.ac.singidunum.novisad.server.services.fakultet.StudijskiProgramService;
import rs.ac.singidunum.novisad.server.services.obavestenje.FajlService;
import rs.ac.singidunum.novisad.server.services.predmet.IshodService;
import rs.ac.singidunum.novisad.server.services.predmet.PlanZaGodinuService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.EvaluacijaZnanjaService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.IspitniRokService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.RealizacijaPredmetaService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.TipEvaluacijeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/evaluacija-znanja")
@Secured({"ROLE_SLUZBA","ROLE_ADMIN","ROLE_STUDENT","ROLE_NASTAVNIK"})
public class EvaluacijaZnanjaController extends GenericController<EvaluacijaZnanja, Long, EvaluacijaZnanjaDto> {

    private final EvaluacijaZnanjaService evaluacijaZnanjaService;
    private final RealizacijaPredmetaService realizacijaPredmetaService;
    private final IshodService ishodService;
    private final FajlService fajlService;
    private final TipEvaluacijeService tipEvaluacijeService;
    private final EvaluacijaZnanjaRepository evaluacijaZnanjaRepository;
    private final PredmetService predmetService;
    private final StudijskiProgramService studijskiProgramService;
    private final IspitniRokService ispitniRokService;
    private final PlanZaGodinuService planZaGodinuService;
    private final TipPolaganjaRepository tipPolaganjaRepository;
    private final NastavnikRepository nastavnikRepository;
    private final PredmetRepository predmetRepository;
    private final FajlRepository fajlRepository;

    public EvaluacijaZnanjaController(GenericService<EvaluacijaZnanja, Long> service, EvaluacijaZnanjaService evaluacijaZnanjaService, RealizacijaPredmetaService realizacijaPredmetaService, IshodService ishodService, FajlService fajlService, TipEvaluacijeService tipEvaluacijeService,
                                      EvaluacijaZnanjaRepository evaluacijaZnanjaRepository, PredmetService predmetService, StudijskiProgramService studijskiProgramService, IspitniRokService ispitniRokService, PlanZaGodinuService planZaGodinuService,
                                      TipPolaganjaRepository tipPolaganjaRepository,
                                      NastavnikRepository nastavnikRepository,
                                      PredmetRepository predmetRepository,
                                      FajlRepository fajlRepository) {
        super(service);
        this.evaluacijaZnanjaService = evaluacijaZnanjaService;
        this.realizacijaPredmetaService = realizacijaPredmetaService;
        this.ishodService = ishodService;
        this.fajlService = fajlService;
        this.tipEvaluacijeService = tipEvaluacijeService;
        this.evaluacijaZnanjaRepository = evaluacijaZnanjaRepository;
        this.predmetService = predmetService;
        this.studijskiProgramService = studijskiProgramService;
        this.ispitniRokService = ispitniRokService;
        this.planZaGodinuService = planZaGodinuService;
        this.tipPolaganjaRepository = tipPolaganjaRepository;
        this.nastavnikRepository = nastavnikRepository;
        this.predmetRepository = predmetRepository;
        this.fajlRepository = fajlRepository;
    }

    @GetMapping("/by-nastavnik-and-predmet")
    public ResponseEntity<List<EvaluacijaZnanjaDto>> getAllByNastavnikAndPredmet(
            @PathParam("nastavnikId") Long nastavnikId,
            @PathParam("predmetId") Long predmetId
    ) throws IllegalAccessException, InstantiationException {
        Nastavnik nastavnik = nastavnikRepository.findById(nastavnikId).orElseThrow();
        Predmet predmet = predmetRepository.findById(predmetId).orElseThrow();

        List<EvaluacijaZnanja> evaluacije = evaluacijaZnanjaRepository.findEvaluacijeZnanjaByNastavnikAndPredmet(nastavnikId, predmetId);
        List<EvaluacijaZnanjaDto> dtos = ListToDto(evaluacije);

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/tipovi-polaganja-k")
    public ResponseEntity<?> getTipoviPolaganjaK(){
        List<TipPolaganja> tipovi = tipPolaganjaRepository.findAllByNazivContains("k");
        return ResponseEntity.ok(tipovi);
    }

    @GetMapping("/nepolagani-kolokvijumi-by-student")
    public ResponseEntity<?> getNepolaganiKolokvijumi(
            @PathParam("predmetId") Long predmetId,
            @PathParam("studentId") Long studentId
    ){
        List<EvaluacijaZnanja> nepolaganiKolokvijumi = evaluacijaZnanjaService.findNepolaganeEvaluacijeZnanja(predmetId, studentId);
        List<EvaluacijaZnanjaDto> dtos = nepolaganiKolokvijumi.stream().map(evaluacijaZnanja -> {
            try {
                return convertToDto(evaluacijaZnanja);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ispit-by-predmet-and-ispitni-rok")
    public ResponseEntity<EvaluacijaZnanjaDto> getIspitByPredmetAndIspitniRok(
        @PathParam("predmetId") Long predmetId,
        @PathParam("ispitniRokId") Long ispitniRokId
    ) throws IllegalAccessException, InstantiationException {
        RealizacijaPredmeta realizacijaPredmeta = realizacijaPredmetaService.findByPredmetId(predmetId);
        IspitniRok ispitniRok = ispitniRokService.findById(ispitniRokId).orElseThrow(() -> new EntityNotFoundException("Ispitni rok not found with id: " + ispitniRokId.toString()));

        EvaluacijaZnanja evaluacijaZnanja = evaluacijaZnanjaService.findIspitByRealizacijaPredmetaAndIspitniRok(realizacijaPredmeta, ispitniRok);
        EvaluacijaZnanjaDto dto = convertToDto(evaluacijaZnanja);

        return ResponseEntity.ok(dto);
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

    @GetMapping("/ispiti")
    public ResponseEntity<List<EvaluacijaZnanjaDto>> getAllIspiti(
            @PathParam("studijskiProgramId") Long studijskiProgramId,
            @PathParam("ispitniRokId") Long ispitniRokId
    ) throws IllegalAccessException, InstantiationException {
        ArrayList<EvaluacijaZnanjaDto> dtoList = new ArrayList<>();
        List<EvaluacijaZnanja> original = evaluacijaZnanjaService.findAllByStudijskiProgramAndTipEvaluacijeAndIspitniRok(studijskiProgramId, ispitniRokId);
        for (EvaluacijaZnanja entity : original) {
            EvaluacijaZnanjaDto dto = convertToDto(entity);
            dtoList.add(dto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("/ispit")
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK","ROLE_SLUZBA"})
    public ResponseEntity<?> createIspit(
            @RequestParam Long predmetId,
            @RequestParam Long ispitniRokId,
            @RequestParam Long studijskiProgramId,
            @RequestBody EvaluacijaZnanjaDto dto) throws IllegalAccessException, InstantiationException {

        try {
            Predmet predmet = predmetService.findById(predmetId).orElseThrow(() -> new RuntimeException("Predmet not found"));
            RealizacijaPredmeta realizacijaPredmeta = realizacijaPredmetaService.findByPredmetId(predmet.getId());
            TipEvaluacije tipEvaluacije = tipEvaluacijeService.findById(dto.getTipEvaluacije().getId()).orElseThrow();
            StudijskiProgram studijskiProgram = studijskiProgramService.findById(studijskiProgramId).orElseThrow(() -> new RuntimeException("Studijski program not found"));
            IspitniRok ispitniRok = ispitniRokService.findById(ispitniRokId).orElseThrow(() -> new RuntimeException("Ispitni rok not found"));

            Integer godinaPredmetaIspita = planZaGodinuService.getGodinaByPredmetAndStudijskiProgram(predmet, studijskiProgram);
            List<EvaluacijaZnanja> postojeciIspiti  = evaluacijaZnanjaService.findAllByStudijskiProgramAndTipEvaluacijeAndIspitniRok(studijskiProgram.getId(), ispitniRok.getId());

            EvaluacijaZnanja entity = convertToEntity(dto);
            entity.setRealizacijaPredmeta(realizacijaPredmeta);
            entity.setTipEvaluacije(tipEvaluacije);
            entity.setIspitniRok(ispitniRok);

            Ishod ishod = new Ishod();
            ishod.setOpis(dto.getIshod().getOpis());
            ishod.setPredmet(realizacijaPredmeta.getPredmet());
            Ishod createdIshod = ishodService.save(ishod);

            entity.setIshod(createdIshod);

            if (entity.getVremeZavrsetka().isBefore(ispitniRok.getPocetak().atStartOfDay()) || entity.getVremePocetka().isAfter(ispitniRok.getKraj().plusDays(1).atStartOfDay())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Datumi ispita nisu u okviru ispitnog roka.");
            }

            if(!postojeciIspiti.isEmpty()) {
                for (EvaluacijaZnanja e : postojeciIspiti) {
                    Integer godina = planZaGodinuService.getGodinaByPredmetAndStudijskiProgram(e.getRealizacijaPredmeta().getPredmet(), studijskiProgram);
                    if (godinaPredmetaIspita.equals(godina) && entity.getVremePocetka().isAfter(e.getVremeZavrsetka()) && entity.getVremeZavrsetka().isBefore(e.getVremePocetka())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Ispiti predmeta koji se pohadjaju na istoj godini studijskog programa ne smeju da budu u istom danu.");
                    }
                    if(entity.getRealizacijaPredmeta().getPredmet().equals(e.getRealizacijaPredmeta().getPredmet()) && entity.getIspitniRok().equals(e.getIspitniRok())){
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Ispit za ovaj predmet u ovom ispitnom roku vec postoji.");
                    }
                }
            }

            EvaluacijaZnanjaDto evaluacijaZnanjaDto = convertToDto(evaluacijaZnanjaService.save(entity));
            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/predmet/{predmetId}")
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK","ROLE_SLUZBA"})
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

    @PutMapping("/{id}/instrument-evaluacije")
    public ResponseEntity<EvaluacijaZnanjaDto> updateInstrumentEvaluacije(@PathVariable Long id, @RequestBody FajlDto fajlDto) throws IllegalAccessException, InstantiationException {
        EvaluacijaZnanja evaluacijaZnanja = evaluacijaZnanjaService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Evaluacija znanja not found with id: " + id.toString())
        );

        Fajl fajl = fajlService.findBySifra(fajlDto.getSifra());
        evaluacijaZnanja.setInstrumentEvaluacije(fajl);

        EvaluacijaZnanja saved = evaluacijaZnanjaService.save(evaluacijaZnanja);
        EvaluacijaZnanjaDto dto = convertToDto(saved);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK","ROLE_SLUZBA"})
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

    private List<EvaluacijaZnanjaDto> ListToDto(List<EvaluacijaZnanja> entities) throws IllegalAccessException, InstantiationException {
        List<EvaluacijaZnanjaDto> dtoList = new ArrayList<>();

        for (EvaluacijaZnanja entity : entities) {
            EvaluacijaZnanjaDto dto = convertToDto(entity);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    @Secured({"ROLE_ADMIN","ROLE_NASTAVNIK","ROLE_SLUZBA"})
    public ResponseEntity<Void> delete(Long aLong) {
        return super.delete(aLong);
    }

    @Override
    protected EvaluacijaZnanjaDto convertToDto(EvaluacijaZnanja entity) throws IllegalAccessException, InstantiationException {
        EvaluacijaZnanjaDto dto = EntityDtoMapper.convertToDto(entity, EvaluacijaZnanjaDto.class);
        if(entity.getIshod() != null) {
            Ishod ishod = ishodService.findById(entity.getIshod().getId()).orElseThrow(
                    () -> new EntityNotFoundException("Ishod not found with id: " + entity.getIshod().getId().toString())
            );
            for(ObrazovniCilj o : ishod.getObrazovniCiljevi()){
                o.setIshod(null);
            }
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
        RealizacijaPredmetaDto realizacijaPredmetaDto = EntityDtoMapper.convertToDto(entity.getRealizacijaPredmeta(), RealizacijaPredmetaDto.class);
        realizacijaPredmetaDto.setObavestenja(null);
        PredmetDto predmetDto = EntityDtoMapper.convertToDto(entity.getRealizacijaPredmeta().getPredmet(), PredmetDto.class);
        predmetDto.setSilabus(null);
        realizacijaPredmetaDto.setPredmet(predmetDto);

        dto.setRealizacijaPredmeta(realizacijaPredmetaDto);

        return dto;
    }

    @Override
    protected EvaluacijaZnanja convertToEntity(EvaluacijaZnanjaDto dto) throws IllegalAccessException, InstantiationException {

        return EntityDtoMapper.convertToEntity(dto, EvaluacijaZnanja.class);
    }
}
