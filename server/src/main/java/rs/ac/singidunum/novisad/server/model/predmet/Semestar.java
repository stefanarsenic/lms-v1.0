package rs.ac.singidunum.novisad.server.model.predmet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.student.SkolskaGodina;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Semestar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer redniBrojSemestra;
    private LocalDateTime pocetakSemestra;
    private LocalDateTime krajSemestra;
    @ManyToOne
    private SkolskaGodina skolskaGodina;
}
