package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.student.PohadjanjePredmetaDto;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PohadjanjePredmeta;

@RestController
@RequestMapping("/api/pohadjanje-predmeta")
public class PohadjanjePredmetaController extends GenericController<PohadjanjePredmeta, Long, PohadjanjePredmetaDto> {
    public PohadjanjePredmetaController(GenericService<PohadjanjePredmeta, Long> service) {
        super(service);
    }

    @Override
    protected PohadjanjePredmetaDto convertToDto(PohadjanjePredmeta entity) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    protected PohadjanjePredmeta convertToEntity(PohadjanjePredmetaDto dto) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
