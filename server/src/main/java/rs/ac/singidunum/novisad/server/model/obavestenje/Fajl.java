package rs.ac.singidunum.novisad.server.model.obavestenje;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Fajl { //TODO: file upload servis

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String opis;
    private String url;
    @ManyToOne
    private Obavestenje obavestenje;
}
