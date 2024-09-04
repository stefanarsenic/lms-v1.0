package rs.ac.singidunum.novisad.server.repositories.obavestenje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;

import java.util.List;

@Repository
public interface ObavestenjeRepository extends JpaRepository<Obavestenje, Long> {

    @Query("select o from Obavestenje o " +
            "left join RealizacijaPredmeta rp " +
            "on o.realizacijaPredmeta.id = rp.id " +
            "where rp.predmet.id = :predmetId")
    List<Obavestenje> findObavestenjeByPredmetId(Long predmetId);
}