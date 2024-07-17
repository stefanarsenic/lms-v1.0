package rs.ac.singidunum.novisad.server.controllers.fakultet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;
import rs.ac.singidunum.novisad.server.dto.fakultet.FakultetDto;
import rs.ac.singidunum.novisad.server.dto.fakultet.UniverzitetDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.fakultet.Fakultet;
import rs.ac.singidunum.novisad.server.model.fakultet.Univerzitet;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.services.adresa.AdresaService;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/univerzitet")
public class UniverzitetController extends GenericController<Univerzitet, Long, UniverzitetDto> {

    private final AdresaService adresaService;
    private final NastavnikService nastavnikService;
    public UniverzitetController(GenericService<Univerzitet, Long> service, AdresaService adresaService, NastavnikService nastavnikService) {
        super(service);
        this.adresaService = adresaService;
        this.nastavnikService = nastavnikService;
    }

    @Override
    protected UniverzitetDto convertToDto(Univerzitet entity) throws IllegalAccessException, InstantiationException {
        UniverzitetDto univerzitetDto = EntityDtoMapper.convertToDto(entity, UniverzitetDto.class);
        univerzitetDto.setFakulteti(Collections.emptySet());
        univerzitetDto.setAdresa(EntityDtoMapper.convertToDto(entity.getAdresa(), AdresaDto.class));

        Set<FakultetDto> fakulteti = new java.util.HashSet<>(Collections.emptySet());

        for(Fakultet fakultet : entity.getFakulteti()){
            fakultet.setUniverzitet(null);
            fakulteti.add(EntityDtoMapper.convertToDto(fakultet, FakultetDto.class));
        }

        univerzitetDto.setFakulteti(fakulteti);

        return univerzitetDto;
    }

    @Override
    protected Univerzitet convertToEntity(UniverzitetDto dto) throws IllegalAccessException, InstantiationException {
        Adresa adresa = adresaService.findById(dto.getAdresa().getId()).orElseThrow();
        Nastavnik nastavnik = nastavnikService.findById(dto.getRektor().getId()).orElseThrow();

        dto.setFakulteti(Collections.emptySet());

        Univerzitet univerzitet = EntityDtoMapper.convertToEntity(dto, Univerzitet.class);
        univerzitet.setAdresa(adresa);
        univerzitet.setRektor(nastavnik);

        return univerzitet;
    }
}
