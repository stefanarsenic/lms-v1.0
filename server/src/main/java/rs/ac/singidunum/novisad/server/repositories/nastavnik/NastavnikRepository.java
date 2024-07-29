package rs.ac.singidunum.novisad.server.repositories.nastavnik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;

import java.util.List;

@Repository
public interface NastavnikRepository extends JpaRepository<Nastavnik, Long> {

    void deleteByIdIn(List<Long> ids);
}