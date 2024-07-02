package rs.ac.singidunum.novisad.server.model.predmet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.student.GodinaStudija;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IspitniRok {

    @Id
    @GeneratedValue
    private Long id;
    private String naziv;
    private LocalDate pocetak;
    private LocalDate kraj;
    @OneToOne
    private GodinaStudija godinaStudija;
}
