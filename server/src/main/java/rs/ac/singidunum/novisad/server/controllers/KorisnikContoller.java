package rs.ac.singidunum.novisad.server.controllers;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rs.ac.singidunum.novisad.server.dto.KorisnikDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.Korisnik;
import rs.ac.singidunum.novisad.server.utils.TokenUtils;

@Controller
@RequestMapping("/api")
public class KorisnikContoller extends GenericController<Korisnik,Long, KorisnikDto> {
    public KorisnikContoller(GenericService<Korisnik, Long> service) {
        super(service);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    protected KorisnikDto convertToDto(Korisnik entity) throws IllegalAccessException, InstantiationException {

        return EntityDtoMapper.convertToDto(entity,KorisnikDto.class);
    }

    @Override
    protected Korisnik convertToEntity(KorisnikDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto,Korisnik.class);
    }

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody KorisnikDto korisnikDto) throws IllegalAccessException, InstantiationException {
        Korisnik korisnik=convertToEntity(korisnikDto);
        System.out.println(userDetailsService.loadUserByUsername(korisnik.getKorisnickoIme()));
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(korisnik.getKorisnickoIme(),korisnik.getLozinka());
        Authentication authentication=authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=tokenUtils.generateToken(userDetailsService.loadUserByUsername(korisnik.getKorisnickoIme()));
        System.out.println(jwt);

        return new ResponseEntity<String>(jwt, HttpStatus.OK);

    }

}
