package rs.ac.singidunum.novisad.server.model.obavestenje;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue
    private Long id;
    private String opis;
    private String url;
    @ManyToOne
    private Obavestenje obavestenje;
}
