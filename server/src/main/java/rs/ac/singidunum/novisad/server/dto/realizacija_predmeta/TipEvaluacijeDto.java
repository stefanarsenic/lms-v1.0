package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipEvaluacijeDto implements Serializable {
    private Long id;
    private String naziv;
}