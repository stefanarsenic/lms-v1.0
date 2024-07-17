package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IshodDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetPlanaZaGodinuDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.predmet.PredmetPlanaZaGodinu;
import rs.ac.singidunum.novisad.server.services.predmet.IshodService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetPlanaZaGodinuService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/predmet")
public class PredmetController extends GenericController<Predmet, Long, PredmetDto>{

    private final IshodService ishodService;
    private final PredmetService predmetService;
    private final PredmetPlanaZaGodinuService predmetPlanaZaGodinuService;
    public PredmetController(GenericService<Predmet, Long> service, IshodService ishodService, PredmetService predmetService, PredmetPlanaZaGodinuService predmetPlanaZaGodinuService) {
        super(service);
        this.ishodService = ishodService;
        this.predmetService = predmetService;
        this.predmetPlanaZaGodinuService = predmetPlanaZaGodinuService;
    }

    @Override
    protected PredmetDto convertToDto(Predmet entity) throws IllegalAccessException, InstantiationException {
        NastavnikDto nastavnikDto = EntityDtoMapper.convertToDto(entity.getNastavnik(), NastavnikDto.class);
        NastavnikDto asistentDto = EntityDtoMapper.convertToDto(entity.getAsistent(), NastavnikDto.class);

        Set<IshodDto> silabusDto = new HashSet<>(Collections.emptySet());
        Set<PredmetDto> preduslovDto = new HashSet<>(Collections.emptySet());
        Set<PredmetPlanaZaGodinuDto> planoviDto = new HashSet<>(Collections.emptySet());

        if(entity.getSilabus() != null){
            for(Ishod ishod : entity.getSilabus()){
                ishod.setPredmet(null);
                silabusDto.add(EntityDtoMapper.convertToDto(ishod, IshodDto.class));
            }
        }
        if(entity.getPreduslov() != null){
            for(Predmet predmet : entity.getPreduslov()){
                preduslovDto.add(EntityDtoMapper.convertToDto(predmet, PredmetDto.class));
            }
        }
        if(entity.getPlanovi() != null){
            for(PredmetPlanaZaGodinu predmetPlanaZaGodinu : entity.getPlanovi()){
                predmetPlanaZaGodinu.setPredmet(null);
                planoviDto.add(EntityDtoMapper.convertToDto(predmetPlanaZaGodinu, PredmetPlanaZaGodinuDto.class));
            }
        }

        PredmetDto predmetDto = EntityDtoMapper.convertToDto(entity, PredmetDto.class);
        predmetDto.setSilabus(silabusDto);
        predmetDto.setPreduslov(preduslovDto);
        predmetDto.setPlanovi(planoviDto);

        return predmetDto;
    }

    @Override
    protected Predmet convertToEntity(PredmetDto dto) throws IllegalAccessException, InstantiationException {
        Set<Ishod> silabus = new HashSet<>(Collections.emptySet());
        Set<Predmet> preduslov = new HashSet<>(Collections.emptySet());
        Set<PredmetPlanaZaGodinu> planovi = new HashSet<>(Collections.emptySet());

        if(dto.getSilabus() != null) {
            for (IshodDto ishodDto : dto.getSilabus()) {
                silabus.add(ishodService.findById(ishodDto.getId()).orElseThrow());
            }
        }
        if(dto.getPreduslov() != null) {
            for(PredmetDto predmetDto : dto.getPreduslov()){
                preduslov.add(predmetService.findById(predmetDto.getId()).orElseThrow());
            }
        }
        if(dto.getPlanovi() != null){
            for(PredmetPlanaZaGodinuDto p : dto.getPlanovi()){
                planovi.add(predmetPlanaZaGodinuService.findById(p.getId()).orElseThrow());
            }
        }

        Predmet predmet = EntityDtoMapper.convertToEntity(dto, Predmet.class);
        predmet.setSilabus(silabus);
        predmet.setPreduslov(preduslov);
        predmet.setPlanovi(planovi);

        return predmet;
    }
}
