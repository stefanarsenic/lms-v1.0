package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.PolozeniPredmetDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.PolozeniPredmet;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.PolozeniPredmetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/polozeni-predmeti")
public class PolozeniPredmetController extends GenericController<PolozeniPredmet, Long, PolozeniPredmetDto> {
    private final PolozeniPredmetService polozeniPredmetService;
    public PolozeniPredmetController(GenericService<PolozeniPredmet, Long> service, PolozeniPredmetService polozeniPredmetService) {
        super(service);
        this.polozeniPredmetService = polozeniPredmetService;
    }

    @GetMapping("/ukupno-espb/student/{studentId}")
    public ResponseEntity<Integer> getUkupnoEspbStudenta(@PathVariable Long studentId){
        Integer ukupnoEspb = polozeniPredmetService.findOstvareniEspbByStudentId(studentId);

        return new ResponseEntity<>(ukupnoEspb, HttpStatus.OK);
    }
    @GetMapping("/prosecna-ocena/student/{studentId}")
    public ResponseEntity<Optional<Double>> getProsecnaOcenaStudenta(@PathVariable Long studentId){
        Optional<Double> prosecnaOcena = polozeniPredmetService.averageKonacnaOcenaByStudentId(studentId);

        return new ResponseEntity<>(prosecnaOcena, HttpStatus.OK);
    }
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PolozeniPredmetDto>> getPolozeniPredmetiByStudentBrojIndeksa(@PathVariable Long studentId){
        List<PolozeniPredmet> polozeniPredmeti = polozeniPredmetService.findPolozeniPredmetsByStudentId(studentId);
        List<PolozeniPredmetDto> dtos = polozeniPredmeti.stream().map(polozeniPredmet -> {
            try {
                PolozeniPredmetDto dto = EntityDtoMapper.convertToDto(polozeniPredmet, PolozeniPredmetDto.class);
                dto.setPredmet(EntityDtoMapper.convertToDto(polozeniPredmet.getPredmet(), PredmetDto.class));
                return dto;
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    protected PolozeniPredmetDto convertToDto(PolozeniPredmet entity) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    protected PolozeniPredmet convertToEntity(PolozeniPredmetDto dto) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
