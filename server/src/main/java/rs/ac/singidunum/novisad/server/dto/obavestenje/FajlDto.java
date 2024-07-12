package rs.ac.singidunum.novisad.server.dto.obavestenje;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FajlDto implements Serializable {
    private Long id;
    private String opis;
    private String url;
    private ObavestenjeDto obavestenje;
}