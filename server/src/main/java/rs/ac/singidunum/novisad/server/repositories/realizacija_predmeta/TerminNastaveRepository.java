package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import jakarta.transaction.Transactional;
import jdk.jfr.StackTrace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;

import java.util.List;

@Repository
public interface TerminNastaveRepository extends JpaRepository<TerminNastave, Long> {


    @Query("select tn from TerminNastave tn " +
            "left join RealizacijaPredmeta rp on tn.realizacijaPredmeta.id = rp.id " +
            "left join NastavnikNaRealizaciji nnr on rp.id = nnr.realizacijaPredmeta.id " +
            "left join TipNastave tip on nnr.tipNastave.id = tip.id " +
            "where nnr.id = :nastavnikId and rp.predmet.id = :predmetId")
    List<TerminNastave> findTerminiNastaveByNastavnikAndPredmet(Long nastavnikId, Long predmetId);

    @Query("SELECT tn " +
            "FROM TerminNastave tn " +
            "WHERE tn.realizacijaPredmeta.predmet.id = :predmetId")
    List<TerminNastave> findTerminiNastaveByPredmetId(@Param("predmetId") Long predmetId);

    @Transactional
    void deleteAllByRealizacijaPredmetaId(Long realizacijaPredmetaId);
}