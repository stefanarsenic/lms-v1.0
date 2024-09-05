package rs.ac.singidunum.novisad.server.dto.student;

import lombok.*;
import rs.ac.singidunum.novisad.server.dto.PravoPristupaDto;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto implements Serializable {
    private Long id;
    private String korisnickoIme;
    private String lozinka;
    private String jmbg;
    private Set<PravoPristupaDto> pravoPristupaSet;
    private String ime;
    private String prezime;
    private String email;
    private AdresaDto adresa;
    private LocalDateTime datumRodjenja;
}