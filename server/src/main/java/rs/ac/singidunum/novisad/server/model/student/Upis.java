package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Upis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datumUpisa;
    private Integer godinaStudija;
    private Integer espbNajava;
    private Integer espbOsvojeno;
    private Integer kojiPut;
    private String studijskiProgram;
    @ManyToOne
    private StudentNaGodini student;
    @ManyToOne
    private SkolskaGodina skolskaGodina;

}
