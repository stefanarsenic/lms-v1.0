package rs.ac.singidunum.novisad.server.controllers.nastavnik;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NaucnaOblastDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.TipZvanjaDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.ZvanjeDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.nastavnik.NaucnaOblast;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipZvanja;
import rs.ac.singidunum.novisad.server.model.nastavnik.Zvanje;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;
import rs.ac.singidunum.novisad.server.services.nastavnik.NaucnaOblastService;
import rs.ac.singidunum.novisad.server.services.nastavnik.TipZvanjaService;

import java.util.Collections;

@RestController
@RequestMapping("/api/zvanje")
public class ZvanjeController extends GenericController<Zvanje, Long, ZvanjeDto> {

    private final NastavnikService nastavnikService;
    private final TipZvanjaService tipZvanjaService;
    private final NaucnaOblastService naucnaOblastService;
    public ZvanjeController(GenericService<Zvanje, Long> service, NastavnikService nastavnikService, TipZvanjaService tipZvanjaService, NaucnaOblastService naucnaOblastService) {
        super(service);
        this.nastavnikService = nastavnikService;
        this.tipZvanjaService = tipZvanjaService;
        this.naucnaOblastService = naucnaOblastService;
    }

    @Override
    protected ZvanjeDto convertToDto(Zvanje entity) throws IllegalAccessException, InstantiationException {
        ZvanjeDto zvanjeDto = EntityDtoMapper.convertToDto(entity, ZvanjeDto.class);
        zvanjeDto.getNastavnik().setZvanja(Collections.emptySet());
        zvanjeDto.setNastavnik(EntityDtoMapper.convertToDto(entity.getNastavnik(), NastavnikDto.class));
        zvanjeDto.setTipZvanja(EntityDtoMapper.convertToDto(entity.getTipZvanja(), TipZvanjaDto.class));
        zvanjeDto.setNacunaOblast(EntityDtoMapper.convertToDto(entity.getNacunaOblast(), NaucnaOblastDto.class));

        return zvanjeDto;
    }

    @Override
    protected Zvanje convertToEntity(ZvanjeDto dto) throws IllegalAccessException, InstantiationException {
        Nastavnik nastavnik = nastavnikService.findById(dto.getNastavnik().getId()).orElseThrow();
        TipZvanja tipZvanja = tipZvanjaService.findById(dto.getTipZvanja().getId()).orElseThrow();
        NaucnaOblast naucnaOblast = naucnaOblastService.findById(dto.getNacunaOblast().getId()).orElseThrow();

        Zvanje zvanje = EntityDtoMapper.convertToEntity(dto, Zvanje.class);
        zvanje.setNastavnik(nastavnik);
        zvanje.setTipZvanja(tipZvanja);
        zvanje.setNacunaOblast(naucnaOblast);

        return zvanje;
    }
}
