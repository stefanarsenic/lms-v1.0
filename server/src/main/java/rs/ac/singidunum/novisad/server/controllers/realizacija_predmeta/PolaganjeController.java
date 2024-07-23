package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PolaganjeDto;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.Polaganje;

@RestController
@RequestMapping("/api/polaganje")
public class PolaganjeController extends GenericController<Polaganje, Long, PolaganjeDto> {
    public PolaganjeController(GenericService<Polaganje, Long> service) {
        super(service);
    }

    @Override
    protected PolaganjeDto convertToDto(Polaganje entity) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    protected Polaganje convertToEntity(PolaganjeDto dto) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
