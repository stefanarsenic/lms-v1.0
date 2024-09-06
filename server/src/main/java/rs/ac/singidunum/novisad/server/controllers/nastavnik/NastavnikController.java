package rs.ac.singidunum.novisad.server.controllers.nastavnik;

import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.PravoPristupaDto;
import rs.ac.singidunum.novisad.server.dto.RegistrovaniKorisnikDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NastavnikDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.NaucnaOblastDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.TipZvanjaDto;
import rs.ac.singidunum.novisad.server.dto.nastavnik.ZvanjeDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.korisnik.PravoPristupa;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.model.korisnik.Uloga;
import rs.ac.singidunum.novisad.server.model.nastavnik.Nastavnik;
import rs.ac.singidunum.novisad.server.model.nastavnik.NaucnaOblast;
import rs.ac.singidunum.novisad.server.model.nastavnik.TipZvanja;
import rs.ac.singidunum.novisad.server.model.nastavnik.Zvanje;
import rs.ac.singidunum.novisad.server.services.nastavnik.NastavnikService;
import rs.ac.singidunum.novisad.server.services.nastavnik.NaucnaOblastService;
import rs.ac.singidunum.novisad.server.services.nastavnik.TipZvanjaService;
import rs.ac.singidunum.novisad.server.services.nastavnik.ZvanjeService;

import java.util.*;

@RestController
@RequestMapping("/api/nastavnici")
@Secured({"ROLE_ADMIN","ROLE_SLUZBA","ROLE_NASTAVNIK"})
public class NastavnikController extends GenericController<Nastavnik, Long, NastavnikDto> {
    public NastavnikController(GenericService<Nastavnik, Long> service) {
        super(service);
    }

    @Autowired
    NastavnikService nastavnikService;

    @Autowired
    NaucnaOblastService naucnaOblastService;

    @Autowired
    ZvanjeService zvanjeService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TipZvanjaService tipZvanjaService;

    @GetMapping("/korisnickoIme")
    public ResponseEntity<NastavnikDto> getByKorisnickoIme(@PathParam("korisnickoIme") String korisnickoIme) throws IllegalAccessException, InstantiationException {
        Nastavnik nastavnik = nastavnikService.findByKorisnickoIme(korisnickoIme).orElseThrow(() -> new EntityNotFoundException("Nastavnik not found with korisnicko ime: " + korisnickoIme));
        NastavnikDto dto = convertToDto(nastavnik);

        return ResponseEntity.ok(dto);
    }


    @Override
    protected NastavnikDto convertToDto(Nastavnik entity) throws IllegalAccessException, InstantiationException {
        NastavnikDto nastavnikDto = EntityDtoMapper.convertToDto(entity, NastavnikDto.class);
        nastavnikDto.setZvanja(Collections.emptySet());
        nastavnikDto.setPravoPristupaSet(null);
        Set<ZvanjeDto> zvanja = new HashSet<>(Collections.emptySet());

        if(entity.getZvanja() != null) {
            for (Zvanje zvanje : entity.getZvanja()) {
                zvanje.setNastavnik(null);
                ZvanjeDto zvanjeDto=EntityDtoMapper.convertToDto(zvanje, ZvanjeDto.class);
                zvanjeDto.setNaucnaOblast(EntityDtoMapper.convertToDto(zvanje.getNaucnaOblast(), NaucnaOblastDto.class));
                zvanjeDto.setTipZvanja(EntityDtoMapper.convertToDto(zvanje.getTipZvanja(), TipZvanjaDto.class));
                zvanja.add(zvanjeDto);
            }
        }

        nastavnikDto.setZvanja(zvanja);

        return nastavnikDto;
    }

    @Override
    protected Nastavnik convertToEntity(NastavnikDto dto) throws IllegalAccessException, InstantiationException {
        return EntityDtoMapper.convertToEntity(dto, Nastavnik.class);
    }


    //TODO:Popraviti DTO response (Nastavnik)
    @Override
    @RequestMapping(path = "/dodaj", method = RequestMethod.POST)
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<NastavnikDto> create(@RequestBody NastavnikDto dto) throws IllegalAccessException, InstantiationException {
        Nastavnik entity = convertToEntity(dto);

        String lozinka = dto.getLozinka();
        entity.setLozinka(passwordEncoder.encode(lozinka));

        Set<Zvanje> zvanjes=new HashSet<>();
        for (ZvanjeDto zvanjeDto:dto.getZvanja()){
            Zvanje zvanje1= new Zvanje();
            zvanje1.setDatumIzbora(zvanjeDto.getDatumIzbora());
            zvanje1.setDatumPrestanka(zvanjeDto.getDatumPrestanka());
            zvanje1.setTipZvanja(tipZvanjaService.findById(zvanjeDto.getTipZvanja().getId()).orElseThrow());
            zvanje1.setNastavnik(entity);
            zvanje1.setNaucnaOblast(EntityDtoMapper.convertToEntity(zvanjeDto.getNaucnaOblast(), NaucnaOblast.class));
            zvanjes.add(zvanje1);
        }

        entity.setZvanja(zvanjes);

        Nastavnik nastavnik=service.save(entity);
        NastavnikDto nastavnikDto=convertToDto(nastavnik);

        return new ResponseEntity<>(nastavnikDto, HttpStatus.CREATED);
    }


    //TODO:Testirati dodatno, izbaci error u konzoli u front
    @DeleteMapping("/delete")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteUsers(@RequestBody List<Long> userIds) {
        nastavnikService.deleteUsers(userIds);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/azuriraj/{id}")
    public ResponseEntity<NastavnikDto> azuriranje(@PathVariable Long id, @RequestBody NastavnikDto dto) throws IllegalAccessException, InstantiationException {
        Nastavnik existingUser = nastavnikService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update basic properties
        existingUser.setKorisnickoIme(dto.getKorisnickoIme());
        existingUser.setEmail(dto.getEmail());
        existingUser.setIme(dto.getIme());
        existingUser.setPrezime(dto.getPrezime());
        existingUser.setBiografija(dto.getBiografija());
        existingUser.setJmbg(dto.getJmbg());

        // Update Zvanja
        Set<Zvanje> existingZvanja = existingUser.getZvanja();
        Set<Zvanje> updatedZvanja = new HashSet<>();

        for (ZvanjeDto zvanjeDto : dto.getZvanja()) {
            Zvanje zvanje;
            if (zvanjeDto.getId() != null) {
                Optional<Zvanje> existingZvanje = existingZvanja.stream()
                        .filter(z -> z.getId().equals(zvanjeDto.getId()))
                        .findFirst();
                if (existingZvanje.isPresent()) {
                    zvanje = existingZvanje.get();
                } else {
                    zvanje = new Zvanje();
                    zvanje.setId(zvanjeDto.getId());
                }
            } else {
                zvanje = new Zvanje();
            }

            zvanje.setDatumIzbora(zvanjeDto.getDatumIzbora());
            zvanje.setDatumPrestanka(zvanjeDto.getDatumPrestanka());
            zvanje.setNastavnik(existingUser);

            // Set TipZvanja if provided in DTO
            if (zvanjeDto.getTipZvanja() != null) {
                Optional<TipZvanja> tipZvanja = tipZvanjaService.findById(zvanjeDto.getTipZvanja().getId());
                tipZvanja.ifPresent(zvanje::setTipZvanja);
            }

            // Set NaucnaOblast if provided in DTO
            if (zvanjeDto.getNaucnaOblast() != null) {
                Optional<NaucnaOblast> naucnaOblast = naucnaOblastService.findById(zvanjeDto.getNaucnaOblast().getId());
                naucnaOblast.ifPresent(zvanje::setNaucnaOblast);
            }

            updatedZvanja.add(zvanje);
        }

        // Remove Zvanja that are no longer in the updated set
        Set<Zvanje> zvanjaToRemove = new HashSet<>(existingZvanja);
        zvanjaToRemove.removeAll(updatedZvanja);

        for (Zvanje zvanjeToRemove : zvanjaToRemove) {
            existingZvanja.remove(zvanjeToRemove);
            zvanjeService.delete(zvanjeToRemove);
        }

        existingZvanja.addAll(updatedZvanja);
        existingUser.setZvanja(existingZvanja);

        // Save the updated user
        Nastavnik savedUser = nastavnikService.save(existingUser);

        // Convert and return the saved user DTO
        NastavnikDto savedDto = convertToDto(savedUser);
        return ResponseEntity.ok(savedDto);
    }




}
