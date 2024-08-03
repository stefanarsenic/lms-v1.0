package rs.ac.singidunum.novisad.server.model.adresa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.adresa.Drzava;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String naziv;
    @ManyToOne
    private Drzava drzava;
}
