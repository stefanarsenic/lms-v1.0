package rs.ac.singidunum.novisad.server.controllers.fakultet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.fakultet.FakultetDto;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.Fakultet;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.services.fakultet.FakultetService;
import rs.ac.singidunum.novisad.server.services.fakultet.StudijskiProgramService;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/studijski-program")
public class StudijskiProgramController extends GenericController<StudijskiProgram, Long, StudijskiProgramDto> {

    private final FakultetService fakultetService;
    private final NastavnikService nastavnikService;
    private final StudijskiProgramService studijskiProgramService;
    public StudijskiProgramController(GenericService<StudijskiProgram, Long> service, FakultetService fakultetService, NastavnikService nastavnikService, StudijskiProgramService studijskiProgramService) {
        super(service);
        this.fakultetService = fakultetService;
        this.nastavnikService = nastavnikService;
        this.studijskiProgramService = studijskiProgramService;
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<StudijskiProgramDto> create(@RequestBody StudijskiProgramDto dto) throws IllegalAccessException, InstantiationException {
        StudijskiProgram studijskiProgram = convertToEntity(dto);
        studijskiProgram = studijskiProgramService.createStudijskiProgram(studijskiProgram);
        StudijskiProgramDto savedDto = convertToDto(studijskiProgram);

        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<StudijskiProgramDto> update(@PathVariable Long id, @RequestBody StudijskiProgramDto dto) throws IllegalAccessException, InstantiationException {
        Optional<StudijskiProgram> entityOptional = service.findById(id);
        if (entityOptional.isPresent()) {
            StudijskiProgram entity = convertToEntity(dto);
            entity = studijskiProgramService.createStudijskiProgram(entity);
            StudijskiProgramDto updatedDto = convertToDto(entity);
            return ResponseEntity.ok(updatedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<StudijskiProgram> studijskiProgram = service.findById(id);
        if (studijskiProgram.isPresent()) {
            StudijskiProgram entity = studijskiProgram.get();
            service.delete(entity);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    protected StudijskiProgramDto convertToDto(StudijskiProgram entity) throws IllegalAccessException, InstantiationException {
        entity.getRukovodilac().setPravoPristupaSet(Collections.emptySet());
        StudijskiProgramDto studijskiProgramDto = EntityDtoMapper.convertToDto(entity, StudijskiProgramDto.class);
        entity.getFakultet().setStudijskiProgrami(Collections.emptySet());
        studijskiProgramDto.setFakultet(EntityDtoMapper.convertToDto(entity.getFakultet(), FakultetDto.class));
        studijskiProgramDto.setRukovodilac(EntityDtoMapper.convertToDto(entity.getRukovodilac(), NastavnikDto.class));

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
