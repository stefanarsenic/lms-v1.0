package rs.ac.singidunum.novisad.server.repositories.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.student.SkolskaGodina;

import java.time.Year;

@Repository
public interface SkolskaGodinaRepository extends JpaRepository<SkolskaGodina, Long> {

    SkolskaGodina findSkolskaGodinaByPocetakSkolskeGodine(Year pocetakSkolskeGodine);
}
