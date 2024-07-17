package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.TipNastaveDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.NastavniMaterijalDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TerminNastaveDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.NastavniMaterijal;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipNastave;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.services.nastavnik.TipNastaveService;
import rs.ac.singidunum.novisad.server.services.predmet.IshodService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.NastavniMaterijalService;

@RestController
@RequestMapping("/api/termin-nastave")
public class TerminNastaveController extends GenericController<TerminNastave, Long, TerminNastaveDto> {

    private final IshodService ishodService;
    private final TipNastaveService tipNastaveService;
    private final NastavniMaterijalService nastavniMaterijalService;
    public TerminNastaveController(GenericService<TerminNastave, Long> service, IshodService ishodService, TipNastaveService tipNastaveService, NastavniMaterijalService nastavniMaterijalService) {
        super(service);
        this.ishodService = ishodService;
        this.tipNastaveService = tipNastaveService;
        this.nastavniMaterijalService = nastavniMaterijalService;
    }

    @Override
    protected TerminNastaveDto convertToDto(TerminNastave entity) throws IllegalAccessException, InstantiationException {
        TerminNastaveDto t = EntityDtoMapper.convertToDto(entity, TerminNastaveDto.class);
        IshodDto ishodDto = EntityDtoMapper.convertToDto(entity.getIshod(), IshodDto.class);
        TipNastaveDto tipNastaveDto = EntityDtoMapper.convertToDto(entity.getTipNastave(), TipNastaveDto.class);
        NastavniMaterijalDto nastavniMaterijalDto = EntityDtoMapper.convertToDto(entity.getNastavniMaterijal(), NastavniMaterijalDto.class);

        t.setIshod(ishodDto);
        t.setTipNastave(tipNastaveDto);
        t.setNastavniMaterijal(nastavniMaterijalDto);

        return t;
    }

    @Override
    protected TerminNastave convertToEntity(TerminNastaveDto dto) throws IllegalAccessException, InstantiationException {
        Ishod ishod = ishodService.findById(dto.getIshod().getId()).orElseThrow();
        TipNastave tipNastave = tipNastaveService.findById(dto.getTipNastave().getId()).orElseThrow();
        NastavniMaterijal nastavniMaterijal = nastavniMaterijalService.findById(dto.getNastavniMaterijal().getId()).orElseThrow();

        TerminNastave t = EntityDtoMapper.convertToEntity(dto, TerminNastave.class);
        t.setIshod(ishod);
        t.setTipNastave(tipNastave);
        t.setNastavniMaterijal(nastavniMaterijal);

        return t;
    }
}
