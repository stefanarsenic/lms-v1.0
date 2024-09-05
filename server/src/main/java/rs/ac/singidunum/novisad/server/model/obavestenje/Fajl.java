package rs.ac.singidunum.novisad.server.model.obavestenje;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Type;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Fajl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String sifra;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String opis;
    private String url;
    @ManyToOne
    private Obavestenje obavestenje;
}
