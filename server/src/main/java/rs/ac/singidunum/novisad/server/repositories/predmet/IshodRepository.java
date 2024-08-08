package rs.ac.singidunum.novisad.server.repositories.predmet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;

import java.util.List;

@Repository
public interface IshodRepository extends JpaRepository<Ishod, Long> {
    void deleteByIdIn(List<Long> ids);
}