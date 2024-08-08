package rs.ac.singidunum.novisad.server.services.predmet;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.predmet.Ishod;
import rs.ac.singidunum.novisad.server.repositories.predmet.IshodRepository;

import java.util.List;

@Service
public class IshodService extends GenericService<Ishod, Long> {

    IshodRepository ishodRepository;
    public IshodService(IshodRepository repository) {
        super(repository);
        ishodRepository=repository;
    }

    @Transactional
    public void deleteUsers(List<Long> userIds) {
        ishodRepository.deleteByIdIn(userIds);
    }
}
