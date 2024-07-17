package rs.ac.singidunum.novisad.server.dto.student;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentskaAktivnostDto implements Serializable {
    private Long id;
    private AktivnostDto aktivnost;
    private StudentNaGodiniDto student;
    private PredmetDto predmet;
    private Double osvojenoPoena;
    private LocalDate datum;
}