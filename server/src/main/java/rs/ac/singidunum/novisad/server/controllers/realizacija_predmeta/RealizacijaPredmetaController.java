package rs.ac.singidunum.novisad.server.controllers.realizacija_predmeta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.novisad.server.dto.obavestenje.FajlDto;
import rs.ac.singidunum.novisad.server.dto.obavestenje.ObavestenjeDto;
import rs.ac.singidunum.novisad.server.dto.predmet.PredmetDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.EvaluacijaZnanjaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.RealizacijaPredmetaDto;
import rs.ac.singidunum.novisad.server.dto.realizacija_predmeta.TerminNastaveDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.EvaluacijaZnanja;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.RealizacijaPredmeta;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;
import rs.ac.singidunum.novisad.server.model.obavestenje.Fajl;
import rs.ac.singidunum.novisad.server.model.obavestenje.Obavestenje;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.services.predmet.PredmetService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.EvaluacijaZnanjaService;
import rs.ac.singidunum.novisad.server.services.realizacija_predmeta.TerminNastaveService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/realizacija-predmeta")
public class RealizacijaPredmetaController extends GenericController<RealizacijaPredmeta, Long, RealizacijaPredmetaDto> {

    private final PredmetService predmetService;
    public RealizacijaPredmetaController(GenericService<RealizacijaPredmeta, Long> service, PredmetService predmetService) {
        super(service);
        this.predmetService = predmetService;

    }

    @Override
    protected RealizacijaPredmetaDto convertToDto(RealizacijaPredmeta entity) throws IllegalAccessException, InstantiationException {
        RealizacijaPredmetaDto r = EntityDtoMapper.convertToDto(entity, RealizacijaPredmetaDto.class);
        PredmetDto predmetDto = EntityDtoMapper.convertToDto(entity.getPredmet(), PredmetDto.class);
        predmetDto.setSilabus(new HashSet<>());
        Set<TerminNastaveDto> terminNastaveSet=new HashSet<>();
        Set<EvaluacijaZnanjaDto> evaluacijaZnanjas=new HashSet<>();
        Set<ObavestenjeDto> obavestenjes=new HashSet<>();
        for(TerminNastave terminNastave:entity.getTerminiNastave()){
            TerminNastaveDto terminNastaveDto=EntityDtoMapper.convertToDto(terminNastave,TerminNastaveDto.class);
            terminNastaveSet.add(terminNastaveDto);
        }
        for(EvaluacijaZnanja evaluacijaZnanja:entity.getEvaluacijeZnanja()){
            EvaluacijaZnanjaDto evaluacijaZnanjaDto=EntityDtoMapper.convertToDto(evaluacijaZnanja,EvaluacijaZnanjaDto.class);
            evaluacijaZnanjas.add(evaluacijaZnanjaDto);
        }
        for(Obavestenje obavestenje:entity.getObavestenja()){
            for(Fajl fajl:obavestenje.getPrilozi()){
                fajl.setObavestenje(null);
            }
            ObavestenjeDto obavestenjeDto=EntityDtoMapper.convertToDto(obavestenje,ObavestenjeDto.class);
            obavestenjes.add(obavestenjeDto);
        }
        r.setObavestenja(obavestenjes);
        r.setEvaluacijaZnanja(evaluacijaZnanjas);
        r.setTerminNastave(terminNastaveSet);
        r.setPredmet(predmetDto);

        return r;
    }

    @Override
    protected RealizacijaPredmeta convertToEntity(RealizacijaPredmetaDto dto) throws IllegalAccessException, InstantiationException {
        Predmet predmet = predmetService.findById(dto.getPredmet().getId()).orElseThrow();

        RealizacijaPredmeta r = EntityDtoMapper.convertToEntity(dto, RealizacijaPredmeta.class);

        predmet.setSilabus(new HashSet<>());
        Set<TerminNastave> terminNastaveSet=new HashSet<>();
        Set<EvaluacijaZnanja> evaluacijaZnanjas=new HashSet<>();
        Set<Obavestenje> obavestenjes=new HashSet<>();
        for(TerminNastaveDto terminNastave:dto.getTerminNastave()){
            TerminNastave terminNastaveDto=EntityDtoMapper.convertToDto(terminNastave,TerminNastave.class);
            terminNastaveSet.add(terminNastaveDto);
        }
        for(EvaluacijaZnanjaDto evaluacijaZnanja:dto.getEvaluacijaZnanja()){
            EvaluacijaZnanja evaluacijaZnanjaDto=EntityDtoMapper.convertToDto(evaluacijaZnanja,EvaluacijaZnanja.class);
            evaluacijaZnanjas.add(evaluacijaZnanjaDto);
        }
        for(ObavestenjeDto obavestenje:dto.getObavestenja()){
            Obavestenje obavestenjeDto=EntityDtoMapper.convertToDto(obavestenje,Obavestenje.class);
            obavestenjes.add(obavestenjeDto);
        }
        r.setObavestenja(obavestenjes);
        r.setEvaluacijeZnanja(evaluacijaZnanjas);
        r.setTerminiNastave(terminNastaveSet);
        r.setPredmet(predmet);


        return r;
    }
}
