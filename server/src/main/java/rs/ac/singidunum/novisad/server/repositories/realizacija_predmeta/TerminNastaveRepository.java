package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;

import java.util.List;

@Repository
public interface TerminNastaveRepository extends JpaRepository<TerminNastave, Long> {

    @Query("SELECT tn " +
            "FROM TerminNastave tn " +
            "WHERE tn.realizacijaPredmeta.predmet.id = :predmetId")
    List<TerminNastave> findTerminiNastaveByPredmetId(@Param("predmetId") Long predmetId);
}