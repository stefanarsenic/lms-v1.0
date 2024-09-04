package rs.ac.singidunum.novisad.server.controllers.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import rs.ac.singidunum.novisad.server.services.korisnik.KorisnikService;
import rs.ac.singidunum.novisad.server.utils.TokenUtils;

import java.util.HashSet;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityController extends GenericController<RegistrovaniKorisnik,Long, RegistrovaniKorisnikDto> {
    public SecurityController(GenericService<RegistrovaniKorisnik, Long> service) {
        super(service);
        this.korisnikService= (KorisnikService) service;
    }

    KorisnikService korisnikService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;


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
                registrovaniKorisnikDto.getPravoPristupaSet().add(pravoPristupaDto);
            }
        }

        return registrovaniKorisnikDto;
    }

    @Override
    protected RegistrovaniKorisnik convertToEntity(RegistrovaniKorisnikDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, RegistrovaniKorisnik.class);
    }


    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody RegistrovaniKorisnikDto korisnikDto) throws IllegalAccessException, InstantiationException {
        RegistrovaniKorisnik korisnik=convertToEntity(korisnikDto);
        System.out.println(userDetailsService.loadUserByUsername(korisnik.getKorisnickoIme()));
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(korisnik.getKorisnickoIme(),korisnik.getLozinka());
        Authentication authentication=authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=tokenUtils.generateToken(userDetailsService.loadUserByUsername(korisnik.getKorisnickoIme()));
        System.out.println(jwt);

        return new ResponseEntity<String>(jwt, HttpStatus.OK);

    }

    @RequestMapping(path = "/username", method = RequestMethod.GET)
    public ResponseEntity<RegistrovaniKorisnikDto> findByUsername(@RequestParam String username) throws IllegalAccessException, InstantiationException {
        Optional<RegistrovaniKorisnik> entityOptional = this.korisnikService.findByKorisnickoIme(username);
        System.out.println(username);
        if (entityOptional.isPresent()) {
            RegistrovaniKorisnik entity = entityOptional.get();
            RegistrovaniKorisnikDto dto = convertToDto(entity);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public ResponseEntity<RegistrovaniKorisnikDto> create(RegistrovaniKorisnikDto dto) throws IllegalAccessException, InstantiationException {
        String lozinka=dto.getLozinka();
        dto.setLozinka(passwordEncoder.encode(lozinka));
        return super.create(dto);
    }

}
