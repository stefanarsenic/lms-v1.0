package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudenskaAktivnost {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Aktivnost aktivnost;
    @OneToOne
    private StudentNaGodini student;
    @OneToOne
    private Predmet predmet;
    private Double osvojenoPoena;
    private LocalDate datum;

}
