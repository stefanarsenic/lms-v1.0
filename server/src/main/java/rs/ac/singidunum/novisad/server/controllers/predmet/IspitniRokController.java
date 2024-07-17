package rs.ac.singidunum.novisad.server.controllers.predmet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.IspitniRokDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.IspitniRok;
import rs.ac.singidunum.novisad.server.model.student.GodinaStudija;
import rs.ac.singidunum.novisad.server.services.student.GodinaStudijaService;

import java.util.Collections;

@RestController
@RequestMapping("/api/ispitni-rok")
public class IspitniRokController extends GenericController<IspitniRok, Long, IspitniRokDto> {

    private final GodinaStudijaService godinaStudijaService;
    public IspitniRokController(GenericService<IspitniRok, Long> service, GodinaStudijaService godinaStudijaService) {
        super(service);
        this.godinaStudijaService = godinaStudijaService;
    }

    @Override
    protected IspitniRokDto convertToDto(IspitniRok entity) throws IllegalAccessException, InstantiationException {
        entity.getGodinaStudija().setStudenti(Collections.emptySet());
        entity.getGodinaStudija().setPlanoviZaGodine(Collections.emptySet());

        StudijskiProgram studijskiProgram = new StudijskiProgram();
        studijskiProgram.setNaziv(entity.getGodinaStudija().getStudijskiProgram().getNaziv());

        entity.getGodinaStudija().setStudijskiProgram(studijskiProgram);

        return EntityDtoMapper.convertToDto(entity, IspitniRokDto.class);
    }

    @Override
    protected IspitniRok convertToEntity(IspitniRokDto dto) throws IllegalAccessException, InstantiationException {
        GodinaStudija godinaStudija = godinaStudijaService.findById(dto.getGodinaStudija().getId()).orElseThrow();

        IspitniRok ispitniRok = EntityDtoMapper.convertToEntity(dto, IspitniRok.class);
        ispitniRok.setGodinaStudija(godinaStudija);

        return ispitniRok;
    }
}
