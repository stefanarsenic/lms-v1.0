package rs.ac.singidunum.novisad.server.model.predmet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ispit {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime datumPolaganja;
    private Time termin;
    private String sala;
    private String poruka;
    @OneToOne
    private IspitniRok ispitniRok;
    @OneToOne
    private Predmet predmet;
}
