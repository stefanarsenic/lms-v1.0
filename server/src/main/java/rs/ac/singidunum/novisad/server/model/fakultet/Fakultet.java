package rs.ac.singidunum.novisad.server.model.fakultet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;

import java.util.Set;


//TODO:Proveriti za svaku kolonu da ne sme biti null itd (za sve entitete)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fakultet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String naziv;
    @ManyToOne
    private Adresa adresa;
    @ManyToOne
    private Univerzitet univerzitet;
    @OneToOne
    private Nastavnik dekan;
    @OneToMany(mappedBy = "fakultet",cascade = CascadeType.ALL)
    private Set<StudijskiProgram> studijskiProgrami;
}
