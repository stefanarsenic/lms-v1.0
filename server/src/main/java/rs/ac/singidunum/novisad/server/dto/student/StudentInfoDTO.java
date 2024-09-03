package rs.ac.singidunum.novisad.server.dto.student;

import java.util.Date;
import java.util.Set;

public class StudentInfoDTO {

    private Integer godinaStudija;
    private Date datumUpisa;
    private Integer espbOsvojeno;
    private Integer espbNajava;
    private String studijskiProgram;
    private Integer pocetakSkolskeGodine;
    private Integer krajSkolskeGodine;

    Set<PolaganjeInfoDto> polaganja;

    private Double proseecnaOcena;

}
