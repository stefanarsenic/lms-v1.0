package rs.ac.singidunum.novisad.server.repositories.predmet;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.predmet.PredmetPlanaZaGodinu;

import java.util.List;

@Repository
public interface PredmetPlanaZaGodinuRepository extends JpaRepository<PredmetPlanaZaGodinu, Long> {
    PredmetPlanaZaGodinu findByPlanZaGodinuAndPredmet(PlanZaGodinu planZaGodinu, Predmet predmet);
    List<PredmetPlanaZaGodinu> findPredmetPlanaZaGodinusByPlanZaGodinu(PlanZaGodinu planZaGodinu);
    @Transactional
    void deleteAllByPlanZaGodinu(PlanZaGodinu planZaGodinu);
}