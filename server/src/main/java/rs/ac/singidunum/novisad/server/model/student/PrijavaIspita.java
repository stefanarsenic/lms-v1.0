package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Student student;
    @OneToOne
    private Ispit ispit;
}
