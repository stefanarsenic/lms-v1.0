package rs.ac.singidunum.novisad.server.dto.nastavnik;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipZvanjaDto implements Serializable {
    private Long id;
    private String naziv;
}