package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GodinaStudija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
