package rs.ac.singidunum.novisad.server.services.korisnik;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.repositories.korisnik.KorisnikRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService extends GenericService<RegistrovaniKorisnik, Long> {
    private final KorisnikRepository korisnikRepository;
    private final PasswordEncoder passwordEncoder;

    public KorisnikService(KorisnikRepository korisnikRepository, @Lazy PasswordEncoder passwordEncoder) {
        super(korisnikRepository);
        this.korisnikRepository = korisnikRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void changePassword(String username, String currentPassword, String newPassword) throws Exception {
        Optional<RegistrovaniKorisnik> userOptional = korisnikRepository.findByKorisnickoIme(username);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        RegistrovaniKorisnik user = userOptional.get();

        if (!passwordEncoder.matches(currentPassword, user.getLozinka())) {
            throw new Exception("Current password is incorrect");
        }

        if (passwordEncoder.matches(newPassword, user.getLozinka())) {
            throw new Exception("New password cannot be the same as the old password");
        }

        user.setLozinka(passwordEncoder.encode(newPassword));
        korisnikRepository.save(user);
    }

    public void changeEmail(String username, String newEmail) throws Exception {
        Optional<RegistrovaniKorisnik> userOptional = korisnikRepository.findByKorisnickoIme(username);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        if (korisnikRepository.existsByEmail(newEmail)) {
            throw new Exception("Email is already in use by another user");
        }

        RegistrovaniKorisnik user = userOptional.get();

        user.setEmail(newEmail);
        korisnikRepository.save(user);
    }

    public Iterable<RegistrovaniKorisnik> findAll() {
        return korisnikRepository.findAll();
    }

    public Optional<RegistrovaniKorisnik> findByKorisnickoIme(String korisnickoIme) {
        return korisnikRepository.findByKorisnickoIme(korisnickoIme);
    }

    @Transactional
    public void deleteUsers(List<Long> userIds) {
        korisnikRepository.deleteByIdIn(userIds);
    }
}
