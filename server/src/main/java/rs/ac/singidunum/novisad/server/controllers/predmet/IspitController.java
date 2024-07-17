package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.IspitDto;
import rs.ac.singidunum.novisad.server.dto.predmet.IspitniRokDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Ispit;
import rs.ac.singidunum.novisad.server.model.predmet.IspitniRok;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.GodinaStudija;
import rs.ac.singidunum.novisad.server.services.predmet.IspitniRokService;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;

@RestController
@RequestMapping("/api/ispit")
public class IspitController extends GenericController<Ispit, Long, IspitDto> {

    private final PredmetService predmetService;
    private final IspitniRokService ispitniRokService;
    public IspitController(GenericService<Ispit, Long> service, PredmetService predmetService, IspitniRokService ispitniRokService) {
        super(service);
        this.predmetService = predmetService;
        this.ispitniRokService = ispitniRokService;
    }

    @Override
    protected IspitDto convertToDto(Ispit entity) throws IllegalAccessException, InstantiationException {
        IspitDto ispitDto = EntityDtoMapper.convertToDto(entity, IspitDto.class);
        ispitDto.setPredmet(EntityDtoMapper.convertToDto(entity.getPredmet(), PredmetDto.class));
        ispitDto.setIspitniRok(EntityDtoMapper.convertToDto(entity.getIspitniRok(), IspitniRokDto.class));

        return ispitDto;
    }

    @Override
    protected Ispit convertToEntity(IspitDto dto) throws IllegalAccessException, InstantiationException {
        Predmet predmet = predmetService.findById(dto.getPredmet().getId()).orElseThrow();
        IspitniRok ispitniRok = ispitniRokService.findById(dto.getIspitniRok().getId()).orElseThrow();

        Ispit ispit = EntityDtoMapper.convertToEntity(dto, Ispit.class);
        ispit.setPredmet(predmet);
        ispit.setIspitniRok(ispitniRok);

        return ispit;
    }
}
