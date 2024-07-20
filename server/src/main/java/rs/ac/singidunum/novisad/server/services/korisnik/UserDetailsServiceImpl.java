package rs.ac.singidunum.novisad.server.services.korisnik;




import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;


import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    KorisnikService korisnikServices;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<RegistrovaniKorisnik> korisnik= korisnikServices.findByKorisnickoIme(username);
        if(korisnik.isPresent()){
            ArrayList<GrantedAuthority> grantedAuthorities=new ArrayList<>();
            for(PravoPristupa pravoPristupa:korisnik.get().getPravoPristupaSet()){
                grantedAuthorities.add(new SimpleGrantedAuthority((pravoPristupa.getUloga().getNaziv())));
            }
            return new User(korisnik.get().getKorisnickoIme(),korisnik.get().getLozinka(),grantedAuthorities);
        }

        return null;
    }
}
