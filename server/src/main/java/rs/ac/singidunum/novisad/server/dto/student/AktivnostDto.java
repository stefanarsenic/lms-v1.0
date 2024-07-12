package rs.ac.singidunum.novisad.server.dto.student;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AktivnostDto implements Serializable {
    private Long id;
    private String promenljiva;
    private String naziv;
    private Integer poenaOd;
    private Integer poenaDo;
}