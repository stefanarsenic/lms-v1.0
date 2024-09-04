package rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.Polaganje;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolaganjeRepository extends JpaRepository<Polaganje, Long> {

    @Query("SELECT count(p.id) " +
            " AS polaganja " +
            " FROM Polaganje p" +
            " LEFT JOIN EvaluacijaZnanja e" +
            " ON p.evaluacijaZnanja.id = e.id " +
            " WHERE e.tipEvaluacije.id = 2 " +
            " AND p.student.id= :studentId AND e.realizacijaPredmeta.predmet.id = :predmetId")
    Optional<Integer> findBrojPolaganjaIspitaByStudentAndPredmet(Long studentId, Long predmetId);

    @Query("SELECT p " +
            "FROM Polaganje p " +
            "LEFT JOIN EvaluacijaZnanja e " +
            "ON p.evaluacijaZnanja.id = e.id " +
            "WHERE e.tipEvaluacije.id = :tipEvaluacijeId " +
            "AND p.student.id = :studentId")
    List<Polaganje> findPolaganjaIspitaByStudentId(Long tipEvaluacijeId, Long studentId);

    @Query("select p from Polaganje p " +
            "left join EvaluacijaZnanja e " +
            "on p.evaluacijaZnanja.id = e.id " +
            "where e.tipEvaluacije.id = 2 " +
            "and p.student.id = :studentId " +
            "and p.bodovi is null")
    List<Polaganje> findPrijavljeniIspitiByStudentId(Long studentId);
}