package rs.ac.singidunum.novisad.server.controllers.security;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.PravoPristupaDto;
import rs.ac.singidunum.novisad.server.dto.RegistrovaniKorisnikDto;
import rs.ac.singidunum.novisad.server.dto.UlogaDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.model.korisnik.Uloga;
import rs.ac.singidunum.novisad.server.services.korisnik.KorisnikService;
import rs.ac.singidunum.novisad.server.services.secuirty.PravoPristupaService;
import rs.ac.singidunum.novisad.server.services.secuirty.UlogaService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/api/korisnici")
@CrossOrigin(origins = "http://localhost:4200")
public class KorisnikController extends GenericController<RegistrovaniKorisnik,Long, RegistrovaniKorisnikDto> {
    public KorisnikController(GenericService<RegistrovaniKorisnik, Long> service) {
        super(service);
    }

    @Autowired
    KorisnikService korisnikService;

    @Autowired
    PravoPristupaService pravoPristupaService;

    @Autowired
    UlogaService ulogaService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    protected RegistrovaniKorisnikDto convertToDto(RegistrovaniKorisnik entity) throws IllegalAccessException, InstantiationException {

        RegistrovaniKorisnikDto registrovaniKorisnikDto=EntityDtoMapper.convertToDto(entity,RegistrovaniKorisnikDto.class);
        registrovaniKorisnikDto.setPravoPristupaSet(new HashSet<>());
        if(!entity.getPravoPristupaSet().isEmpty())
        {
            for(PravoPristupa p:entity.getPravoPristupaSet()){
                RegistrovaniKorisnikDto registrovaniKorisnik=new RegistrovaniKorisnikDto();
                registrovaniKorisnik.setId(entity.getId());
                UlogaDto ulogaDto=EntityDtoMapper.convertToDto(p.getUloga(),UlogaDto.class);
                PravoPristupaDto pravoPristupaDto=new PravoPristupaDto();
                pravoPristupaDto.setUloga(ulogaDto);
                pravoPristupaDto.setRegistrovaniKorisnikDto(registrovaniKorisnik);
                registrovaniKorisnikDto.getPravoPristupaSet().add(pravoPristupaDto);
            }
        }

        return registrovaniKorisnikDto;
    }

    @Override
    protected RegistrovaniKorisnik convertToEntity(RegistrovaniKorisnikDto dto) throws IllegalAccessException, InstantiationException {
        RegistrovaniKorisnik registrovaniKorisnik=EntityDtoMapper.convertToEntity(dto,RegistrovaniKorisnik.class);

        registrovaniKorisnik.setPravoPristupaSet(new HashSet<>());
        if(!dto.getPravoPristupaSet().isEmpty())
        {
            for(PravoPristupaDto pravoPristupaDto:dto.getPravoPristupaSet()){
                RegistrovaniKorisnik registrovaniKorisnik1=new RegistrovaniKorisnik();
                registrovaniKorisnik1.setId(dto.getId());
                Uloga uloga=EntityDtoMapper.convertToEntity(pravoPristupaDto.getUloga(),Uloga.class);
                PravoPristupa pravoPristupa=new PravoPristupa();
                pravoPristupa.setRegistrovaniKorisnik(registrovaniKorisnik1);
                pravoPristupa.setUloga(uloga);
                registrovaniKorisnik.getPravoPristupaSet().add(pravoPristupa);
            }
        }
        return registrovaniKorisnik;
    }

    @Override
    @RequestMapping(path = "/dodaj", method = RequestMethod.POST)
    public ResponseEntity<RegistrovaniKorisnikDto> create(@RequestBody RegistrovaniKorisnikDto dto) throws IllegalAccessException, InstantiationException {
        RegistrovaniKorisnik entity = convertToEntity(dto);

        String lozinka = dto.getLozinka();
        entity.setLozinka(passwordEncoder.encode(lozinka));

        Set<PravoPristupa> pravoPristupaSet = new HashSet<>();
        for (PravoPristupaDto pravoPristupaDto : dto.getPravoPristupaSet()) {
            PravoPristupa pravoPristupa = new PravoPristupa();
            pravoPristupa.setUloga(ulogaService.findById(pravoPristupaDto.getUloga().getId()).orElse(null));
            pravoPristupa.setRegistrovaniKorisnik(entity);
            pravoPristupaSet.add(pravoPristupa);
        }

        entity.setPravoPristupaSet(pravoPristupaSet);

        RegistrovaniKorisnik savedEntity = service.save(entity);

        RegistrovaniKorisnikDto savedDto = convertToDto(savedEntity);

        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }


    //TODO:Spoj ovo u front end
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUsers(@RequestBody List<Long> userIds) {
        korisnikService.deleteUsers(userIds);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/azuriaj/{id}")
    public ResponseEntity<RegistrovaniKorisnikDto> azuriranje(@PathVariable Long id, @RequestBody RegistrovaniKorisnikDto dto) throws IllegalAccessException, InstantiationException {
        RegistrovaniKorisnik existingUser = korisnikService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setKorisnickoIme(dto.getKorisnickoIme());
        existingUser.setEmail(dto.getEmail());
        existingUser.setIme(dto.getIme());
        existingUser.setPrezime(dto.getPrezime());

        Set<PravoPristupa> existingRoles = existingUser.getPravoPristupaSet();
        Set<PravoPristupa> updatedRoles = new HashSet<>();

        for (PravoPristupaDto pravoPristupaDto : dto.getPravoPristupaSet()) {
            Optional<Uloga> uloga = ulogaService.findById(pravoPristupaDto.getUloga().getId());
            if (uloga.isPresent()) {
                PravoPristupa pravoPristupa = new PravoPristupa();
                if (pravoPristupaDto.getId() != null) {
                    Optional<PravoPristupa> existingRole = pravoPristupaService.findById(pravoPristupaDto.getId());
                    if (existingRole.isPresent()) {
                        pravoPristupa = existingRole.get();
                    }
                }
                pravoPristupa.setUloga(uloga.get());
                pravoPristupa.setRegistrovaniKorisnik(existingUser);
                updatedRoles.add(pravoPristupa);
            }
        }

        Set<PravoPristupa> rolesToRemove = new HashSet<>(existingRoles);
        rolesToRemove.removeAll(updatedRoles);

        for (PravoPristupa roleToRemove : rolesToRemove) {
            existingRoles.remove(roleToRemove);
            pravoPristupaService.delete(roleToRemove);
        }

        existingRoles.addAll(updatedRoles);

        existingUser.setPravoPristupaSet(existingRoles);

        RegistrovaniKorisnik savedUser = korisnikService.save(existingUser);

        RegistrovaniKorisnikDto savedDto = EntityDtoMapper.convertToDto(savedUser, RegistrovaniKorisnikDto.class);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }



}
