package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.IspitniRok;
@Repository
public interface IspitniRokRepository extends JpaRepository<IspitniRok, Long> {
}