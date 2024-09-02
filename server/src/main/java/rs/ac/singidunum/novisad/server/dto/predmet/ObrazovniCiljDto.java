package rs.ac.singidunum.novisad.server.dto.predmet;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObrazovniCiljDto implements Serializable{
    private Long id;
    private String opis;
    private IshodDto ishod;
}
