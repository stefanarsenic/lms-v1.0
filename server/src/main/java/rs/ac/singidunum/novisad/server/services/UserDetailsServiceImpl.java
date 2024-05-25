package rs.ac.singidunum.novisad.server.services;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.model.Korisnik;
import rs.ac.singidunum.novisad.server.model.PravoPristupa;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    KorisnikServices korisnikServices;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Korisnik> korisnik= korisnikServices.findByKorisnickoIme(username);
        if(korisnik.isPresent()){
            ArrayList<GrantedAuthority> grantedAuthorities=new ArrayList<>();
            for(PravoPristupa pravoPristupa:korisnik.get().getPravoPristupaSet()){
                grantedAuthorities.add(new SimpleGrantedAuthority((pravoPristupa.getNaziv())));
            }
            return new User(korisnik.get().getKorisnickoIme(),korisnik.get().getLozinka(),grantedAuthorities);
        }

        return null;
    }
}
