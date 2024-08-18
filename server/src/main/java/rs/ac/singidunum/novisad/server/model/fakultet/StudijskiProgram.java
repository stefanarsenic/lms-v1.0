package rs.ac.singidunum.novisad.server.model.fakultet;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudijskiProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String naziv;
    private Integer godineTrajanja;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String opis;
    @ManyToOne
    private Fakultet fakultet;
    @OneToOne
    private Nastavnik rukovodilac;
    @OneToMany(mappedBy = "studijskiProgram", cascade = CascadeType.ALL)
    private List<StudentNaGodini> studentiNaGodini = new ArrayList<>();
}
