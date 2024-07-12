package rs.ac.singidunum.novisad.server.dto.student;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.predmet.IspitDto;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolozeniIspitiDto implements Serializable {
    private Long id;
    private Integer ocena;
    private Double bodovi;
    private StudentDto student;
    private IspitDto ispit;
}