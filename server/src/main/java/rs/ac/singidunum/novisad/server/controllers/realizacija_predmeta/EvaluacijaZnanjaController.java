package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.EvaluacijaZnanjaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TipEvaluacijeDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TipEvaluacije;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.services.obavestenje.FajlService;
import rs.ac.singidunum.novisad.server.services.predmet.IshodService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.TipEvaluacijeService;

@RestController
@RequestMapping("/api/evaluacija-znanja")
public class EvaluacijaZnanjaController extends GenericController<EvaluacijaZnanja, Long, EvaluacijaZnanjaDto> {

    private final IshodService ishodService;
    private final FajlService fajlService;
    private final TipEvaluacijeService tipEvaluacijeService;
    public EvaluacijaZnanjaController(GenericService<EvaluacijaZnanja, Long> service, IshodService ishodService, FajlService fajlService, TipEvaluacijeService tipEvaluacijeService) {
        super(service);
        this.ishodService = ishodService;
        this.fajlService = fajlService;
        this.tipEvaluacijeService = tipEvaluacijeService;
    }

    @Override
    protected EvaluacijaZnanjaDto convertToDto(EvaluacijaZnanja entity) throws IllegalAccessException, InstantiationException {
        IshodDto ishodDto = EntityDtoMapper.convertToDto(entity.getIshod(), IshodDto.class);
        FajlDto fajlDto = EntityDtoMapper.convertToDto(entity.getInstrumentEvaluacije(), FajlDto.class);
        TipEvaluacijeDto tipEvaluacijeDto = EntityDtoMapper.convertToDto(entity.getTipEvaluacije(), TipEvaluacijeDto.class);

        EvaluacijaZnanjaDto e = EntityDtoMapper.convertToDto(entity, EvaluacijaZnanjaDto.class);
        e.setIshod(ishodDto);
        e.setInstrumentEvaluacije(fajlDto);
        e.setTipEvaluacije(tipEvaluacijeDto);

        return e;
    }

    @Override
    protected EvaluacijaZnanja convertToEntity(EvaluacijaZnanjaDto dto) throws IllegalAccessException, InstantiationException {
        Ishod ishod = ishodService.findById(dto.getIshod().getId()).orElseThrow();
        Fajl fajl = fajlService.findById(dto.getInstrumentEvaluacije().getId()).orElseThrow();
        TipEvaluacije tipEvaluacije = tipEvaluacijeService.findById(dto.getInstrumentEvaluacije().getId()).orElseThrow();

        EvaluacijaZnanja e = EntityDtoMapper.convertToEntity(dto, EvaluacijaZnanja.class);
        e.setIshod(ishod);
        e.setInstrumentEvaluacije(fajl);
        e.setTipEvaluacije(tipEvaluacije);

        return e;
    }
}
