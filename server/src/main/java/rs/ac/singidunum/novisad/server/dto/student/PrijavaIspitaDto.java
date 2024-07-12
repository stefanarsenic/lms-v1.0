package rs.ac.singidunum.novisad.server.dto.student;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.predmet.IspitDto;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrijavaIspitaDto implements Serializable {
    private Long id;
    private StudentDto student;
    private IspitDto ispit;
}