package rs.ac.singidunum.novisad.server.repositories.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.dto.student.StudentInfoDTO;
import rs.ac.singidunum.novisad.server.model.student.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT d.naziv " +
            "FROM Student s " +
            "LEFT JOIN s.adresa a " +
            "LEFT JOIN a.mesto m " +
            "LEFT JOIN m.drzava d " +
            "WHERE s.id = :studentId")
    String findNazivDrzaveByStudentId(@Param("studentId") Long studentId);


    @Query(value = """
        SELECT
            s.id AS studentId,
            rk.ime,
            rk.prezime,
            s.jmbg,
            s.datum_rodjenja AS datumRodjenja,
            a.ulica,
            a.broj,
            m.naziv AS mesto,
            d.naziv AS drzava,
            sng.godina_studija AS godinaStudija,
            u.datum_upisa AS datumUpisa,
            u.espb_osvojeno AS espbOsvojeno,
            u.espb_najava AS espbNajava,
            sp.naziv AS studijskiProgram,
            sg.pocetak_skolske_godine AS pocetakSkolskeGodine,
            sg.kraj_skolske_godine AS krajSkolskeGodine,
            p.naziv AS predmet,
            poh.konacna_ocena AS konacnaOcena,
            po.bodovi AS bodoviNaPolaganju,
            ez.vreme_pocetka AS pocetakEvaluacije,
            ez.vreme_zavrsetka AS zavrsetakEvaluacije,
            ir.naziv AS ispitniRok,
            CASE
                WHEN po.bodovi < 15 THEN 0
                ELSE 1
            END AS statusPolaganja,
            po.napomena AS napomenaNaPolaganju,
            COALESCE((
                     SELECT AVG(poh_inner.konacna_ocena)
                     FROM pohadjanje_predmeta poh_inner
                     WHERE poh_inner.student_id = sng.id AND poh_inner.konacna_ocena > 5
                 ), 0) AS prosecnaOcena
        FROM
            student s
            JOIN registrovani_korisnik rk ON s.id = rk.id
            LEFT JOIN adresa a ON s.adresa_id = a.id
            LEFT JOIN mesto m ON a.mesto_id = m.id
            LEFT JOIN drzava d ON m.drzava_id = d.id
            LEFT JOIN student_na_godini sng ON s.id = sng.student_id
            LEFT JOIN upis u ON sng.id = u.student_id
            LEFT JOIN studijski_program sp ON sng.studijski_program_id = sp.id
            LEFT JOIN skolska_godina sg ON u.skolska_godina_id = sg.id
            LEFT JOIN pohadjanje_predmeta poh ON sng.id = poh.student_id
            LEFT JOIN predmet p ON poh.predmet_id = p.id
            LEFT JOIN polaganje po ON poh.student_id = po.student_id
            LEFT JOIN evaluacija_znanja ez ON po.evaluacija_znanja_id = ez.id
            LEFT JOIN ispitni_rok ir ON ez.ispitni_rok_id = ir.id
        WHERE
            s.id = :studentId
        """, nativeQuery = true)
    List<Object[]> getStudentInfo(@Param("studentId") Long studentId);




}