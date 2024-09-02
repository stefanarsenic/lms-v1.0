package rs.ac.singidunum.novisad.server.model.predmet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.NastavniMaterijal;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ishod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(nullable = false)
    private String opis;
    @ManyToOne
    private Predmet predmet;
    @ManyToOne
    private NastavniMaterijal nastavniMaterijal;
    @OneToMany(mappedBy = "ishod")
    private List<ObrazovniCilj> obrazovniCiljevi;
}
