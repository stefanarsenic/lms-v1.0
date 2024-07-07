package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentNaGodini {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datumUpisa;
    private String brojIndeksa;
    @OneToOne
    private Student student;
    @ManyToOne
    private GodinaStudija godinaStudija;
}
