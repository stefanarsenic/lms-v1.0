package rs.ac.singidunum.novisad.server.services.nastavnik;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.repositories.korisnik.KorisnikRepository;
import rs.ac.singidunum.novisad.server.repositories.nastavnik.NastavnikRepository;

import java.util.List;

@Service
public class NastavnikService extends GenericService<Nastavnik, Long> {

    private final NastavnikRepository nastavnikRepository;
    public NastavnikService(NastavnikRepository nastavnikRepository) {
        super(nastavnikRepository);
        this.nastavnikRepository = nastavnikRepository;
    }

    @Transactional
    public void deleteUsers(List<Long> userIds) {
        nastavnikRepository.deleteByIdIn(userIds);
    }
}
