package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.EvaluacijaZnanjaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.RealizacijaPredmetaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TerminNastaveDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.EvaluacijaZnanjaService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.TerminNastaveService;

@RestController
@RequestMapping("/api/realizacija-predmeta")
public class RealizacijaPredmetaController extends GenericController<RealizacijaPredmeta, Long, RealizacijaPredmetaDto> {

    private final PredmetService predmetService;
    private final TerminNastaveService terminNastaveService;
    private final EvaluacijaZnanjaService evaluacijaZnanjaService;
    public RealizacijaPredmetaController(GenericService<RealizacijaPredmeta, Long> service, PredmetService predmetService, TerminNastaveService terminNastaveService, EvaluacijaZnanjaService evaluacijaZnanjaService) {
        super(service);
        this.predmetService = predmetService;
        this.terminNastaveService = terminNastaveService;
        this.evaluacijaZnanjaService = evaluacijaZnanjaService;
    }

    @Override
    protected RealizacijaPredmetaDto convertToDto(RealizacijaPredmeta entity) throws IllegalAccessException, InstantiationException {
        RealizacijaPredmetaDto r = EntityDtoMapper.convertToDto(entity, RealizacijaPredmetaDto.class);
        PredmetDto predmetDto = EntityDtoMapper.convertToDto(entity.getPredmet(), PredmetDto.class);
        TerminNastaveDto terminNastaveDto = EntityDtoMapper.convertToDto(entity.getTerminNastave(), TerminNastaveDto.class);
        EvaluacijaZnanjaDto evaluacijaZnanjaDto = EntityDtoMapper.convertToDto(entity.getEvaluacijaZnanja(), EvaluacijaZnanjaDto.class);

        r.setPredmet(predmetDto);
        r.setTerminNastave(terminNastaveDto);
        r.setEvaluacijaZnanja(evaluacijaZnanjaDto);

        return r;
    }

    @Override
    protected RealizacijaPredmeta convertToEntity(RealizacijaPredmetaDto dto) throws IllegalAccessException, InstantiationException {
        Predmet predmet = predmetService.findById(dto.getPredmet().getId()).orElseThrow();
        TerminNastave terminNastave = terminNastaveService.findById(dto.getTerminNastave().getId()).orElseThrow();
        EvaluacijaZnanja evaluacijaZnanja = evaluacijaZnanjaService.findById(dto.getEvaluacijaZnanja().getId()).orElseThrow();

        RealizacijaPredmeta r = EntityDtoMapper.convertToEntity(dto, RealizacijaPredmeta.class);
        r.setPredmet(predmet);
        r.setTerminNastave(terminNastave);
        r.setEvaluacijaZnanja(evaluacijaZnanja);

        return r;
    }
}
