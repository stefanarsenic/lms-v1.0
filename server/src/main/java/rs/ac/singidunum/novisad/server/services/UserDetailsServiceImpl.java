package rs.ac.singidunum.novisad.server.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.model.korisnik.Privilegija;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.model.korisnik.Uloga;
import rs.ac.singidunum.novisad.server.repositories.korisnik.UlogaRepository;
import rs.ac.singidunum.novisad.server.services.korisnik.KorisnikService;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final KorisnikService korisnikService;
    private final UlogaRepository ulogaRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        RegistrovaniKorisnik korisnik = korisnikService.findByEmail(email);
        if (korisnik == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(Collections.singletonList(
                            ulogaRepository.findByNaziv("ROLE_USER"))));
        }

        return new org.springframework.security.core.userdetails.User(
                korisnik.getEmail(), korisnik.getLozinka(), korisnik.isEnabled(), true, true,
                true, getAuthorities(korisnik.getUloge()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Uloga> uloge) {

        return getGrantedAuthorities(getPrivileges(uloge));
    }

    private List<String> getPrivileges(Collection<Uloga> uloge) {

        List<String> privilegije = new ArrayList<>();
        List<Privilegija> collection = new ArrayList<>();
        for (Uloga uloga : uloge) {
            privilegije.add(uloga.getNaziv());
            collection.addAll(uloga.getPrivilegije());
        }
        for (Privilegija privilegija : collection) {
            privilegije.add(privilegija.getNaziv());
        }
        return privilegije;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privilegije) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilegija : privilegije) {
            authorities.add(new SimpleGrantedAuthority(privilegija));
        }
        return authorities;
    }
}
