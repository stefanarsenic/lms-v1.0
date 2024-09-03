package rs.ac.singidunum.novisad.server.repositories.predmet;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.model.predmet.ObrazovniCilj;

@Repository
public interface ObrazovniCiljRepository extends JpaRepository<ObrazovniCilj, Long> {

    @Transactional
    void deleteAllByIshodId(Long ishodId);
}