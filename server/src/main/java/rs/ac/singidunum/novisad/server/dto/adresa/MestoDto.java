package rs.ac.singidunum.novisad.server.dto.adresa;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MestoDto implements Serializable {
    private Long id;
    private String naziv;
    private DrzavaDto drzava;
}