package rs.ac.singidunum.novisad.server.controllers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.RegistrovaniKorisnikDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.services.korisnik.KorisnikService;
import rs.ac.singidunum.novisad.server.services.secuirty.PravoPristupaService;


@Controller
@RequestMapping("/api/korisnici")
@CrossOrigin(origins = "http://localhost:4200")
public class KorisnikController extends GenericController<RegistrovaniKorisnik,Long, RegistrovaniKorisnikDto> {
    public KorisnikController(GenericService<RegistrovaniKorisnik, Long> service) {
        super(service);
    }


    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    protected RegistrovaniKorisnikDto convertToDto(RegistrovaniKorisnik entity) throws IllegalAccessException, InstantiationException {

        return EntityDtoMapper.convertToDto(entity,RegistrovaniKorisnikDto.class);
    }

    @Override
    protected RegistrovaniKorisnik convertToEntity(RegistrovaniKorisnikDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, RegistrovaniKorisnik.class);
    }

    KorisnikService korisnikService;

    @Autowired
    PravoPristupaService pravoPristupaService;

    @Override
    @RequestMapping(path = "/dodaj",method = RequestMethod.POST)
    public ResponseEntity<RegistrovaniKorisnikDto> create(RegistrovaniKorisnikDto dto) throws IllegalAccessException, InstantiationException {
        RegistrovaniKorisnik entity = convertToEntity(dto);
        String lozinka=dto.getLozinka();
        entity.setLozinka(passwordEncoder.encode(lozinka));
        entity = service.save(entity);
        RegistrovaniKorisnikDto savedDto = convertToDto(entity);

        //TODO:Ne cuvaj pravo pristupa
        for(PravoPristupa p:entity.getPravoPristupaSet()){
            p.setRegistrovaniKorisnik(entity);
            pravoPristupaService.save(p);
        }
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }
}
