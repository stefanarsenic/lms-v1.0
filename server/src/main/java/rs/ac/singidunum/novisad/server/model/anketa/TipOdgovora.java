package rs.ac.singidunum.novisad.server.model.anketa;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TipOdgovora {

    @Id
    @GeneratedValue
    private Long id;
    private String tip;
}
