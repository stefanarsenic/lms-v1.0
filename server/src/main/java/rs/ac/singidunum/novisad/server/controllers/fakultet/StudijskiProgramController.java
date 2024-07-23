package rs.ac.singidunum.novisad.server.controllers.fakultet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.fakultet.FakultetDto;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.Fakultet;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.services.fakultet.FakultetService;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/studijski-program")
public class StudijskiProgramController extends GenericController<StudijskiProgram, Long, StudijskiProgramDto> {

    private final FakultetService fakultetService;
    private final NastavnikService nastavnikService;
    public StudijskiProgramController(GenericService<StudijskiProgram, Long> service, FakultetService fakultetService, NastavnikService nastavnikService) {
        super(service);
        this.fakultetService = fakultetService;
        this.nastavnikService = nastavnikService;
    }

    @Override
    protected StudijskiProgramDto convertToDto(StudijskiProgram entity) throws IllegalAccessException, InstantiationException {
        StudijskiProgramDto studijskiProgramDto = EntityDtoMapper.convertToDto(entity, StudijskiProgramDto.class);
        entity.getFakultet().setStudijskiProgrami(Collections.emptySet());
        studijskiProgramDto.setFakultet(EntityDtoMapper.convertToDto(entity.getFakultet(), FakultetDto.class));

        return studijskiProgramDto;
    }

    @Override
    protected StudijskiProgram convertToEntity(StudijskiProgramDto dto) throws IllegalAccessException, InstantiationException {
        Fakultet fakultet = fakultetService.findById(dto.getFakultet().getId()).orElseThrow();
        Nastavnik nastavnik = nastavnikService.findById(dto.getRukovodilac().getId()).orElseThrow();

        StudijskiProgram studijskiProgram = EntityDtoMapper.convertToEntity(dto, StudijskiProgram.class);
        studijskiProgram.setRukovodilac(nastavnik);
        studijskiProgram.setFakultet(fakultet);

        return studijskiProgram;
    }
}
