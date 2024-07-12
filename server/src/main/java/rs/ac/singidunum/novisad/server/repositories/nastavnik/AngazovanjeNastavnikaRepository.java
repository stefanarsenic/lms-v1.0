package rs.ac.singidunum.novisad.server.repositories.nastavnik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AngazovanjeNastavnikaRepository extends JpaRepository<AngazovanjeNastavnika, Long> {
}