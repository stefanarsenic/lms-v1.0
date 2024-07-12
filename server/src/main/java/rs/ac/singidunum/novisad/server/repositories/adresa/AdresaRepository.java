package rs.ac.singidunum.novisad.server.repositories.adresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;

@Repository
public interface AdresaRepository extends JpaRepository<Adresa, Long> {
}