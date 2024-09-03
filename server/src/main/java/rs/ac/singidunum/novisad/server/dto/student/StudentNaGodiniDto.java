package rs.ac.singidunum.novisad.server.dto.student;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentNaGodiniDto implements Serializable {
    private Long id;
    private LocalDateTime datumUpisa;
    private LocalDateTime datumZavrsetka;
    private String brojIndeksa;
    private Integer godinaStudija;
    private StudentDto student;
    private StudijskiProgramDto studijskiProgram;
    private List<PohadjanjePredmetaDto> predmeti;
}