package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.IspitniRokDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.IspitniRok;

import java.util.Collections;

@RestController
@RequestMapping("/api/ispitni-rok")
public class IspitniRokController extends GenericController<IspitniRok, Long, IspitniRokDto> {

    public IspitniRokController(GenericService<IspitniRok, Long> service) {
        super(service);
    }

    @Override
    protected IspitniRokDto convertToDto(IspitniRok entity) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToDto(entity, IspitniRokDto.class);
    }

    @Override
    protected IspitniRok convertToEntity(IspitniRokDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, IspitniRok.class);
    }
}
