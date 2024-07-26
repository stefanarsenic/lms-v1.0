package rs.ac.singidunum.novisad.server.repositories.predmet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;

import java.util.List;

@Repository
public interface PlanZaGodinuRepository extends JpaRepository<PlanZaGodinu, Long> {

    PlanZaGodinu findByStudijskiProgramAndGodina(StudijskiProgram studijskiProgram, Integer godina);
    List<PlanZaGodinu> findPlanZaGodinusByStudijskiProgram(StudijskiProgram studijskiProgram);
    void deletePlanZaGodinusByStudijskiProgram(StudijskiProgram studijskiProgram);
}