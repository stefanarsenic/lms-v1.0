package rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TipEvaluacije {

    @Id
    @GeneratedValue
    private Long id;
    private String naziv;
}
