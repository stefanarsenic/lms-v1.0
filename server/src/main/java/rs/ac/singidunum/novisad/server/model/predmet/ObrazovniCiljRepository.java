package rs.ac.singidunum.novisad.server.model.predmet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObrazovniCiljRepository extends JpaRepository<ObrazovniCilj, Long> {
}