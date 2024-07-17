package rs.ac.singidunum.novisad.server.controllers.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.fakultet.StudijskiProgramDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PlanZaGodinuDto;
import rs.ac.singidunum.novisad.server.dto.student.GodinaStudijaDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentNaGodiniDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.fakultet.StudijskiProgram;
import rs.ac.singidunum.novisad.server.model.predmet.PlanZaGodinu;
import rs.ac.singidunum.novisad.server.model.student.GodinaStudija;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.model.student.StudentNaGodini;
import rs.ac.singidunum.novisad.server.services.fakultet.StudijskiProgramService;
import rs.ac.singidunum.novisad.server.services.predmet.PlanZaGodinuService;
import rs.ac.singidunum.novisad.server.services.student.StudentNaGodiniService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/godina-studija")
public class GodinaStudijaController extends GenericController<GodinaStudija, Long, GodinaStudijaDto> {

    private final StudentNaGodiniService studentNaGodiniService;
    private final PlanZaGodinuService planZaGodinuService;
    private final StudijskiProgramService studijskiProgramService;
    public GodinaStudijaController(GenericService<GodinaStudija, Long> service, StudentNaGodiniService studentNaGodiniService, PlanZaGodinuService planZaGodinuService, StudijskiProgramService studijskiProgramService) {
        super(service);
        this.studentNaGodiniService = studentNaGodiniService;
        this.planZaGodinuService = planZaGodinuService;
        this.studijskiProgramService = studijskiProgramService;
    }

    @Override
    protected GodinaStudijaDto convertToDto(GodinaStudija entity) throws IllegalAccessException, InstantiationException {
        GodinaStudijaDto g = EntityDtoMapper.convertToDto(entity, GodinaStudijaDto.class);

        Set<StudentNaGodiniDto> studenti = new HashSet<>(Collections.emptySet());
        Set<PlanZaGodinuDto> planovi = new HashSet<>(Collections.emptySet());

        if(entity.getStudenti() != null){
            for(StudentNaGodini studentNaGodini : entity.getStudenti()){
                studenti.add(EntityDtoMapper.convertToDto(studentNaGodini, StudentNaGodiniDto.class));
            }
        }
        if(entity.getPlanoviZaGodine() != null){
            for(PlanZaGodinu planZaGodinu : entity.getPlanoviZaGodine()){
                planovi.add(EntityDtoMapper.convertToDto(planZaGodinu, PlanZaGodinuDto.class));
            }
        }

        g.setStudenti(studenti);
        g.setPlanoviZaGodine(planovi);
        g.setStudijskiProgram(EntityDtoMapper.convertToDto(entity.getStudijskiProgram(), StudijskiProgramDto.class));

        return g;
    }

    @Override
    protected GodinaStudija convertToEntity(GodinaStudijaDto dto) throws IllegalAccessException, InstantiationException {
        GodinaStudija g = EntityDtoMapper.convertToEntity(dto, GodinaStudija.class);
        StudijskiProgram studijskiProgram = studijskiProgramService.findById(dto.getStudijskiProgram().getId()).orElseThrow();

        Set<StudentNaGodini> studenti = new HashSet<>(Collections.emptySet());
        Set<PlanZaGodinu> planovi = new HashSet<>(Collections.emptySet());

        if(dto.getStudenti() != null){
            for(StudentNaGodiniDto studentNaGodini : dto.getStudenti()){
                studenti.add(studentNaGodiniService.findById(studentNaGodini.getId()).orElseThrow());
            }
        }
        if(dto.getPlanoviZaGodine() != null){
            for(PlanZaGodinuDto planZaGodinu : dto.getPlanoviZaGodine()){
                planovi.add(planZaGodinuService.findById(planZaGodinu.getId()).orElseThrow());
            }
        }

        g.setStudenti(studenti);
        g.setPlanoviZaGodine(planovi);
        g.setStudijskiProgram(studijskiProgram);

        return g;
    }
}
