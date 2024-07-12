package rs.ac.singidunum.novisad.server.dto.student;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentNaGodiniDto implements Serializable {
    private Long id;
    private LocalDateTime datumUpisa;
    private String brojIndeksa;
    private StudentDto student;
    private GodinaStudijaDto godinaStudija;
}