package rs.ac.singidunum.novisad.server.controllers.adresa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;
import rs.ac.singidunum.novisad.server.dto.adresa.DrzavaDto;
import rs.ac.singidunum.novisad.server.dto.adresa.MestoDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.adresa.Mesto;
import rs.ac.singidunum.novisad.server.services.adresa.MestoService;

import java.util.Collections;

@RestController
@RequestMapping("/api/adresa")
public class AdresaController extends GenericController<Adresa, Long, AdresaDto> {

    private final MestoService mestoService;
    public AdresaController(GenericService<Adresa, Long> service, MestoService mestoService) {
        super(service);
        this.mestoService = mestoService;
    }

    @Override
    protected AdresaDto convertToDto(Adresa entity) throws IllegalAccessException, InstantiationException {
        entity.getMesto().getDrzava().setMesta(Collections.emptySet());

        AdresaDto adresaDto = EntityDtoMapper.convertToDto(entity, AdresaDto.class);
        adresaDto.setMesto(EntityDtoMapper.convertToDto(entity.getMesto(), MestoDto.class));

        return adresaDto;
    }

    @Override
    protected Adresa convertToEntity(AdresaDto dto) throws IllegalAccessException, InstantiationException {
        Mesto mesto = mestoService.findById(dto.getMesto().getId()).orElseThrow();
        mesto.getDrzava().setMesta(Collections.emptySet());
        dto.setMesto(null);

        Adresa adresa = EntityDtoMapper.convertToEntity(dto, Adresa.class);
        adresa.setMesto(mesto);

        return adresa;
    }
}
