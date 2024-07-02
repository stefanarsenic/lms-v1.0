package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.predmet.Ispit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PrijavaIspita {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Student student;
    @OneToOne
    private Ispit ispit;
}
