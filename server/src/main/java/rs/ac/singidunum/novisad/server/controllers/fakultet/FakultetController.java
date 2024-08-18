package rs.ac.singidunum.novisad.server.controllers.fakultet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.fakultet.FakultetDto;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.fakultet.Fakultet;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.fakultet.Univerzitet;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.services.adresa.AdresaService;
import rs.ac.singidunum.novisad.server.services.fakultet.UniverzitetService;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/fakultet")
public class FakultetController extends GenericController<Fakultet, Long, FakultetDto> {

    private final UniverzitetService univerzitetService;
    private final NastavnikService nastavnikService;
    private final AdresaService adresaService;
    public FakultetController(GenericService<Fakultet, Long> service, UniverzitetService univerzitetService, NastavnikService nastavnikService, AdresaService adresaService) {
        super(service);
        this.univerzitetService = univerzitetService;
        this.nastavnikService = nastavnikService;
        this.adresaService = adresaService;
    }

    @Override
    protected FakultetDto convertToDto(Fakultet entity) throws IllegalAccessException, InstantiationException {
        entity.getUniverzitet().setFakulteti(Collections.emptySet());
        FakultetDto fakultetDto = EntityDtoMapper.convertToDto(entity, FakultetDto.class);
        fakultetDto.setStudijskiProgrami(Collections.emptySet());
        //fakultetDto.setNastavnik(EntityDtoMapper.convertToDto(entity.getDekan(), NastavnikDto.class));
        //fakultetDto.setAdresa(EntityDtoMapper.convertToDto(entity.getAdresa(), AdresaDto.class));
        //fakultetDto.setUniverzitet(EntityDtoMapper.convertToDto(entity.getUniverzitet(), UniverzitetDto.class));

        Set<StudijskiProgramDto> studijskiProgramDtos = new HashSet<>(Collections.emptySet());

        for(StudijskiProgram studijskiProgram : entity.getStudijskiProgrami()){
            studijskiProgram.setFakultet(null);
            studijskiProgram.getRukovodilac().setPravoPristupaSet(null);
            StudijskiProgramDto studijskiProgramDto = EntityDtoMapper.convertToDto(studijskiProgram, StudijskiProgramDto.class);
            studijskiProgramDto.setRukovodilac(EntityDtoMapper.convertToDto(studijskiProgram.getRukovodilac(), NastavnikDto.class));
            studijskiProgramDtos.add(studijskiProgramDto);
        }

        fakultetDto.setStudijskiProgrami(studijskiProgramDtos);

        return fakultetDto;
    }

    @Override
    protected Fakultet convertToEntity(FakultetDto dto) throws IllegalAccessException, InstantiationException {
        Nastavnik nastavnik = nastavnikService.findById(dto.getNastavnik().getId()).orElseThrow();
        Univerzitet univerzitet = univerzitetService.findById(dto.getUniverzitet().getId()).orElseThrow();
        Adresa adresa = adresaService.findById(dto.getAdresa().getId()).orElseThrow();

        Fakultet fakultet = EntityDtoMapper.convertToEntity(dto, Fakultet.class);
        fakultet.setDekan(nastavnik);
        fakultet.setUniverzitet(univerzitet);
        fakultet.setStudijskiProgrami(Collections.emptySet());
        fakultet.setAdresa(adresa);

        return fakultet;
    }
}
