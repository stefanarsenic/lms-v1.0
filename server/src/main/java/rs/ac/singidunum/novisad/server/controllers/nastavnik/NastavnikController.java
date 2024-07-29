package rs.ac.singidunum.novisad.server.controllers.nastavnik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.ZvanjeDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.nastavnik.NaucnaOblast;
import rs.ac.singidunum.novisad.server.model.nastavnik.Zvanje;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;
import rs.ac.singidunum.novisad.server.services.nastavnik.TipZvanjaService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/nastavnici")
public class NastavnikController extends GenericController<Nastavnik, Long, NastavnikDto> {
    public NastavnikController(GenericService<Nastavnik, Long> service) {
        super(service);
    }

    @Autowired
    NastavnikService nastavnikService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TipZvanjaService tipZvanjaService;

    @Override
    protected NastavnikDto convertToDto(Nastavnik entity) throws IllegalAccessException, InstantiationException {
        NastavnikDto nastavnikDto = EntityDtoMapper.convertToDto(entity, NastavnikDto.class);
        nastavnikDto.setZvanja(Collections.emptySet());
        nastavnikDto.setPravoPristupaSet(null);
        Set<ZvanjeDto> zvanja = new HashSet<>(Collections.emptySet());

        if(entity.getZvanja() != null) {
            for (Zvanje zvanje : entity.getZvanja()) {
                zvanje.setNastavnik(null);
                zvanja.add(EntityDtoMapper.convertToDto(zvanje, ZvanjeDto.class));
            }
        }

        nastavnikDto.setZvanja(zvanja);

        return nastavnikDto;
    }

    @Override
    protected Nastavnik convertToEntity(NastavnikDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, Nastavnik.class);
    }


    //TODO:Popraviti DTO response (Nastavnik)
    @Override
    @RequestMapping(path = "/dodaj", method = RequestMethod.POST)
    public ResponseEntity<NastavnikDto> create(@RequestBody NastavnikDto dto) throws IllegalAccessException, InstantiationException {
        Nastavnik entity = convertToEntity(dto);

        String lozinka = dto.getLozinka();
        entity.setLozinka(passwordEncoder.encode(lozinka));

        Set<Zvanje> zvanjes=new HashSet<>();
        for (ZvanjeDto zvanjeDto:dto.getZvanja()){
            Zvanje zvanje1= new Zvanje();
            zvanje1.setDatumIzbora(zvanjeDto.getDatumIzbora());
            zvanje1.setDatumPrestanka(zvanjeDto.getDatumPrestanka());
            zvanje1.setTipZvanja(tipZvanjaService.findById(zvanjeDto.getTipZvanja().getId()).orElseThrow());
            zvanje1.setNastavnik(entity);
            zvanje1.setNaucnaOblast(EntityDtoMapper.convertToEntity(zvanjeDto.getNaucnaOblast(), NaucnaOblast.class));
            zvanjes.add(zvanje1);
        }

        entity.setZvanja(zvanjes);

        Nastavnik nastavnik=service.save(entity);
        NastavnikDto nastavnikDto=EntityDtoMapper.convertToDto(nastavnik,NastavnikDto.class);

        return new ResponseEntity<>(nastavnikDto, HttpStatus.CREATED);
    }


    //TODO:Testirati dodatno, izbaci error u konzoli u front
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUsers(@RequestBody List<Long> userIds) {
        nastavnikService.deleteUsers(userIds);
        return ResponseEntity.noContent().build();
    }

}
