package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.IspitniRok;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolaganjePredmeta;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.util.List;

@Repository
public interface PolaganjePredmetaRepository extends JpaRepository<PolaganjePredmeta, Long> {

    PolaganjePredmeta findByStudentAndPredmetAndIspitniRok(StudentNaGodini student, Predmet predmet, IspitniRok ispitniRok);

    @Query("SELECT pp " +
            "FROM PolaganjePredmeta pp " +
            "WHERE pp.student.id = :studentId " +
            "AND pp.konacnaOcena < 6")
    List<PolaganjePredmeta> findNeuspesnaPolaganjaByStudentId(Long studentId);

    @Query("SELECT count(pp) " +
            "FROM PolaganjePredmeta pp " +
            "WHERE pp.student.id = :studentId " +
            "AND pp.predmet.id = :predmetId")
    Integer findBrojPolaganjaByStudentAndPredmet(Long studentId, Long predmetId);

    @Query("SELECT SUM(p.bodovi) " +
            "FROM PolaganjePredmeta pp " +
            "LEFT JOIN PohadjanjePredmetaHasPolaganja pphp " +
            "ON pp.id = pphp.pohadjanjePredmeta.id " +
            "LEFT JOIN Polaganje p " +
            "ON pphp.polaganje.id = p.id " +
            "WHERE p.student.id = :studentId " +
            "AND pp.predmet.id = :predmetId")
    Integer getBodoviPolaganjaByPredmetAndStudent(@Param("studentId") Long studentId,
                                                  @Param("predmetId") Long predmetId);

}
