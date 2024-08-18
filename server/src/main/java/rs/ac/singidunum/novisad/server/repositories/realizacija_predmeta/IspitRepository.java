package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.Ispit;

import java.util.List;

@Repository
public interface IspitRepository extends JpaRepository<Ispit, Long> {

    List<Ispit> findAllByIspitniRokIdAndStudijskiProgramId(Long ispitniRokId, Long studijskiProgramId);
}