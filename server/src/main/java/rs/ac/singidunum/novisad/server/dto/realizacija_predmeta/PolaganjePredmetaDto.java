package rs.ac.singidunum.novisad.server.dto.realizacija_predmeta;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolaganjePredmetaDto implements Serializable {

        private Long id;
        private Integer konacnaOcena;
        private StudentNaGodiniDto student;
        private PredmetDto predmet;
        private Integer brojPolaganja;
        private Integer bodovi;
        private List<PolaganjeDto> polaganja;
        private IspitniRokDto ispitniRok;
}
