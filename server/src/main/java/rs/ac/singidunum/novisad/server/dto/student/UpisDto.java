package rs.ac.singidunum.novisad.server.dto.student;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.model.student.SkolskaGodina;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpisDto implements Serializable {
    private Long id;
    private LocalDateTime datumUpisa;
    private Integer godinaStudija;
    private Integer espbNajava;
    private Integer espbOsvojeno;
    private Integer kojiPut;
    private String studijskiProgram;
    private StudentNaGodiniDto student;
    private SkolskaGodina skolskaGodina;
}
