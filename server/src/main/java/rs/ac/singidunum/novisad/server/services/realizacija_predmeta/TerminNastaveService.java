package rs.ac.singidunum.novisad.server.services.realizacija_predmeta;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.novisad.server.dto.event.EventDto;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.NastavnikNaRealizaciji;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.NastavnikNaRealizacijiRepository;
import rs.ac.singidunum.novisad.server.model.RealizacijaPredmeta.TerminNastave;
import rs.ac.singidunum.novisad.server.model.predmet.Predmet;
import rs.ac.singidunum.novisad.server.repositories.predmet.PredmetRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.RealizacijaPredmetaRepository;
import rs.ac.singidunum.novisad.server.repositories.realizacija_predmeta.TerminNastaveRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class TerminNastaveService extends GenericService<TerminNastave, Long> {
    private final TerminNastaveRepository terminNastaveRepository;
    private final RealizacijaPredmetaRepository realizacijaPredmetaRepository;
    private final NastavnikNaRealizacijiRepository nastavnikNaRealizacijiRepository;
    private final PredmetRepository predmetRepository;

    public TerminNastaveService(CrudRepository<TerminNastave, Long> repository, TerminNastaveRepository terminNastaveRepository,
                                RealizacijaPredmetaRepository realizacijaPredmetaRepository,
                                NastavnikNaRealizacijiRepository nastavnikNaRealizacijiRepository,
                                PredmetRepository predmetRepository) {
        super(repository);
        this.terminNastaveRepository = terminNastaveRepository;
        this.realizacijaPredmetaRepository = realizacijaPredmetaRepository;
        this.nastavnikNaRealizacijiRepository = nastavnikNaRealizacijiRepository;
        this.predmetRepository = predmetRepository;
    }

    public List<TerminNastave> findTerminiNastaveByNastavnikAndPredmet(Long nastavnikId, Long predmetId){
        NastavnikNaRealizaciji nastavnikNaRealizaciji = nastavnikNaRealizacijiRepository.findByNastavnikId(nastavnikId).orElseThrow(() -> new EntityNotFoundException("Nastavnik not found with id: " + nastavnikId.toString()));
        Predmet predmet = predmetRepository.findById(predmetId).orElseThrow(() -> new EntityNotFoundException("Predmet not found with id: " + predmetId.toString()));

        return terminNastaveRepository.findTerminiNastaveByNastavnikAndPredmet(nastavnikNaRealizaciji.getId(), predmet.getId());
    }
    public List<TerminNastave> findByPredmet(Long predmetId){
        return terminNastaveRepository.findTerminiNastaveByPredmetId(predmetId);
    }
    public void deleteAllByRealizacijaPredmetaId(Long realizacijaPredmetaId){
        realizacijaPredmetaRepository.findById(realizacijaPredmetaId).orElseThrow(
            () -> new EntityNotFoundException("Realizacija predmeta not found with id: " + realizacijaPredmetaId.toString())
        );

        terminNastaveRepository.deleteAllByRealizacijaPredmetaId(realizacijaPredmetaId);
    }
    public TerminNastave create(TerminNastave terminNastave){
        return terminNastaveRepository.save(terminNastave);
    }
    @Transactional
    public List<TerminNastave> createRecurring(TerminNastave terminNastave, EventDto eventDto){
        LocalDateTime dtstart = eventDto.getDtstart();
        LocalDate until = eventDto.getUntil();
        List<DayOfWeek> recurrenceDays = parseDays(eventDto.getByweekday());

        LocalTime startTime = dtstart.toLocalTime();
        LocalTime endTime = startTime.plusHours(eventDto.getDuration());

        List<TerminNastave> termini = new ArrayList<>();

        LocalDateTime occurrence = dtstart;

        while (occurrence.isBefore(until.atStartOfDay())) {

            if (recurrenceDays.contains(occurrence.getDayOfWeek())) {
                TerminNastave termin = new TerminNastave();
                termin.setVremePocetka(LocalDateTime.of(occurrence.toLocalDate(), startTime));
                termin.setVremeZavrsetka(LocalDateTime.of(occurrence.toLocalDate(), endTime));
                termin.setTipNastave(terminNastave.getTipNastave());
                termin.setRealizacijaPredmeta(terminNastave.getRealizacijaPredmeta());

                termini.add(termin);
                terminNastaveRepository.save(termin);
            }

            occurrence = occurrence.plusDays(1);
        }

        return termini;
    }
    private List<DayOfWeek> parseDays(List<String> days) {
        List<DayOfWeek> daysOfWeek = new ArrayList<>();

        for (String day : days) {
            switch (day.trim().toLowerCase()) {
                case "mo":
                    daysOfWeek.add(DayOfWeek.MONDAY);
                    break;
                case "tu":
                    daysOfWeek.add(DayOfWeek.TUESDAY);
                    break;
                case "we":
                    daysOfWeek.add(DayOfWeek.WEDNESDAY);
                    break;
                case "th":
                    daysOfWeek.add(DayOfWeek.THURSDAY);
                    break;
                case "fr":
                    daysOfWeek.add(DayOfWeek.FRIDAY);
                    break;
                case "sa":
                    daysOfWeek.add(DayOfWeek.SATURDAY);
                    break;
                case "su":
                    daysOfWeek.add(DayOfWeek.SUNDAY);
                    break;
                default:
                    break;
            }
        }

        return daysOfWeek;
    }
}
