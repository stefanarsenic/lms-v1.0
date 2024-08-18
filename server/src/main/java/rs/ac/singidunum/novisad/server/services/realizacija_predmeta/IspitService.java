package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.Ispit;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.IspitRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.IspitniRokRepository;

import java.util.List;

@Service
public class IspitService extends GenericService<Ispit, Long> {

    private final IspitRepository ispitRepository;
    public IspitService(CrudRepository<Ispit, Long> repository, IspitRepository ispitRepository) {
        super(repository);
        this.ispitRepository = ispitRepository;
    }

    public List<Ispit> findAllByIspitniRokIdAndStudijskiProgramId(Long ispitniRokId, Long studijskiProgramId){
        return ispitRepository.findAllByIspitniRokIdAndStudijskiProgramId(ispitniRokId, studijskiProgramId);
    }
}
