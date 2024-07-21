package rs.ac.singidunum.novisad.server.dto.student;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto implements Serializable {
    private Long id;
    private String korisnickoIme;
    private String lozinka;
    private Long jmbg;
    private String ime;
    private String prezime;
    private String email;
    private AdresaDto adresa;
    private LocalDateTime datumRodjenja;
    private GodinaStudijaDto godinaStudija;
}