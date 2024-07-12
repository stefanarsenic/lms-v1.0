package rs.ac.singidunum.novisad.server.dto.adresa;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdresaDto implements Serializable {
    private Long id;
    private String ulica;
    private String broj;
    private MestoDto mesto;
}