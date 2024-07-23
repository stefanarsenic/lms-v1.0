package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PrijavaIspitaDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PrijavaIspita;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.EvaluacijaZnanjaService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.IspitniRokService;
import rs.ac.singidunum.novisad.server.services.student.StudentNaGodiniService;
import rs.ac.singidunum.novisad.server.services.student.StudentService;

@RestController
@RequestMapping("/api/prijava-ispita")
public class PrijavaIspitaController extends GenericController<PrijavaIspita, Long, PrijavaIspitaDto> {

    private final StudentNaGodiniService studentNaGodiniService;
    private final EvaluacijaZnanjaService evaluacijaZnanjaService;
    private final IspitniRokService ispitniRokService;

    public PrijavaIspitaController(GenericService<PrijavaIspita, Long> service, StudentNaGodiniService studentNaGodiniService, EvaluacijaZnanjaService evaluacijaZnanjaService, IspitniRokService ispitniRokService) {
        super(service);
        this.studentNaGodiniService = studentNaGodiniService;
        this.evaluacijaZnanjaService = evaluacijaZnanjaService;
        this.ispitniRokService = ispitniRokService;
    }

    @Override
    protected PrijavaIspitaDto convertToDto(PrijavaIspita entity) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    protected PrijavaIspita convertToEntity(PrijavaIspitaDto dto) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
