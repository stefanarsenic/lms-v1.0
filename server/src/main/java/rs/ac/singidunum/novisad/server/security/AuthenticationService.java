package rs.ac.singidunum.novisad.server.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.services.korisnik.KorisnikService;
import rs.ac.singidunum.novisad.server.services.korisnik.UserDetailsServiceImpl;
import rs.ac.singidunum.novisad.server.utils.TokenUtils;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final KorisnikService korisnikService;
    private final TokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationResponse register(RegistrovaniKorisnik korisnik){
        RegistrovaniKorisnik registrovaniKorisnik = new RegistrovaniKorisnik();
        registrovaniKorisnik.setKorisnickoIme(korisnik.getKorisnickoIme());
        registrovaniKorisnik.setLozinka(passwordEncoder.encode(korisnik.getLozinka()));

        korisnikService.save(registrovaniKorisnik);
        String token = tokenUtils.generateToken(userDetailsService.loadUserByUsername(registrovaniKorisnik.getKorisnickoIme()));

        return new AuthenticationResponse(token);
    }
}
