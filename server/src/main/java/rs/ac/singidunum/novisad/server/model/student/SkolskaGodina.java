package rs.ac.singidunum.novisad.server.model.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SkolskaGodina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Year pocetakSkolskeGodine;
    private Year krajSkolskeGodine;
}
