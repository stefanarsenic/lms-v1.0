package rs.ac.singidunum.novisad.server.controllers.studentskaSluzba;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.AdministratorDto;
import rs.ac.singidunum.novisad.server.dto.RegistrovaniKorisnikDto;
import rs.ac.singidunum.novisad.server.dto.studentskaSluzba.ZahtevMaterijalaDto;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.model.korisnik.Administrator;
import rs.ac.singidunum.novisad.server.model.korisnik.RegistrovaniKorisnik;
import rs.ac.singidunum.novisad.server.model.studetnskaSluzba.ZahtevMaterijala;
import rs.ac.singidunum.novisad.server.repositories.korisnik.KorisnikRepository;
import rs.ac.singidunum.novisad.server.repositories.secuirty.AdministratorRepository;
import rs.ac.singidunum.novisad.server.repositories.studentskaSluzba.ZahtevMaterijalaRepository;
import rs.ac.singidunum.novisad.server.services.studentskaSluzba.ZahtevMaterijalaService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/zahtevi")
public class ZahtevMaterijalaController extends GenericController<ZahtevMaterijala,Long, ZahtevMaterijalaDto> {


    public ZahtevMaterijalaController(ZahtevMaterijalaService service) {
        super(service);
    }

    @Override
    protected ZahtevMaterijalaDto convertToDto(ZahtevMaterijala entity) throws IllegalAccessException, InstantiationException {
        ZahtevMaterijalaDto dto = new ZahtevMaterijalaDto();
        try {
            dto.setId(entity.getId());
            dto.setOpis(entity.getOpis());
            dto.setStatus(entity.getStatus());
            dto.setNaslov(entity.getNaslov());
            RegistrovaniKorisnikDto registrovaniKorisnikDto= EntityDtoMapper.convertToDto(entity.getKorisnik(),RegistrovaniKorisnikDto.class);
            registrovaniKorisnikDto.setPravoPristupaSet(new HashSet<>());
            dto.setKorisnik(registrovaniKorisnikDto);  // Assuming korisnik has an "ime" field
            if (entity.getAdmin() != null) {
                AdministratorDto admin= EntityDtoMapper.convertToDto(entity.getAdmin(),AdministratorDto.class);
                dto.setAdmin(admin);
            }
            dto.setDatumPodnosenja(entity.getDatumPodnosenja());
            dto.setDatumIzmena(entity.getDatumIzmena());
        }catch (IllegalAccessException | InstantiationException e){
            e.printStackTrace();
        }

        return dto;
    }

    @Override
    protected ZahtevMaterijala convertToEntity(ZahtevMaterijalaDto dto) throws IllegalAccessException, InstantiationException {
        ZahtevMaterijala entity = new ZahtevMaterijala();
        try {
            entity.setId(dto.getId());  // If you're setting ID manually, be cautious. Usually, ID is auto-generated.
            entity.setOpis(dto.getOpis());
            entity.setStatus(dto.getStatus());
            entity.setNaslov(dto.getNaslov());

            // Convert Korisnik (assuming you have a similar utility to convert from Dto to Entity)
            if (dto.getKorisnik() != null) {
                RegistrovaniKorisnik korisnikEntity = EntityDtoMapper.convertToEntity(dto.getKorisnik(), RegistrovaniKorisnik.class);
                entity.setKorisnik(korisnikEntity);
            }

            // Convert Admin (if present)
            if (dto.getAdmin() != null) {
                Administrator adminEntity = EntityDtoMapper.convertToEntity(dto.getAdmin(), Administrator.class);
                entity.setAdmin(adminEntity);
            }

            // Set Dates
            entity.setDatumPodnosenja(dto.getDatumPodnosenja());
            entity.setDatumIzmena(dto.getDatumIzmena());

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Autowired
    private ZahtevMaterijalaRepository trebovanjeRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private AdministratorRepository adminRepository;  // Assuming a repository for admins

    @PostMapping("/submit")
    public ResponseEntity<ZahtevMaterijalaDto> submitRequest(@RequestBody ZahtevMaterijalaDto trebovanjeDTO) throws IllegalAccessException, InstantiationException {
        // Fetch the user making the request
        RegistrovaniKorisnik korisnik = korisnikRepository.findById(trebovanjeDTO.getKorisnik().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ZahtevMaterijala trebovanje = new ZahtevMaterijala();
        trebovanje.setNaslov(trebovanjeDTO.getNaslov());
        trebovanje.setOpis(trebovanjeDTO.getOpis());
        trebovanje.setKorisnik(korisnik);
        trebovanje.setDatumPodnosenja(new Timestamp(System.currentTimeMillis()));
        trebovanje.setStatus("PENDING");

        ZahtevMaterijala savedTrebovanje = trebovanjeRepository.save(trebovanje);
        return ResponseEntity.ok(convertToDto(savedTrebovanje));
    }

//    @SneakyThrows
//    @GetMapping("/user/{korisnikId}")
//    public ResponseEntity<List<ZahtevMaterijalaDto>> getUserRequests(@PathVariable Long korisnikId) throws IllegalAccessException, InstantiationException{
//        List<ZahtevMaterijala> requests = trebovanjeRepository.findByKorisnikId(korisnikId);
//        List<ZahtevMaterijalaDto> dtoList = new ArrayList<>();
//        for (ZahtevMaterijala zahtev : requests) {
//            ZahtevMaterijalaDto dto = convertToDto(zahtev);
//            dtoList.add(dto);
//        }
//
//        return ResponseEntity.ok(dtoList);
//    }


    @Override
    @RequestMapping("/update/{id}")
    public ResponseEntity<ZahtevMaterijalaDto> update(@PathVariable Long id, @RequestBody ZahtevMaterijalaDto dto) throws IllegalAccessException, InstantiationException {
        return super.update(id, dto);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ZahtevMaterijalaDto>> getRequestsByUsername(@PathVariable String username) throws IllegalAccessException, InstantiationException {
        List<ZahtevMaterijala> requests = trebovanjeRepository.findByKorisnikKorisnickoIme(username);
        List<ZahtevMaterijalaDto> dtoList = new ArrayList<>();
        for (ZahtevMaterijala zahtev : requests) {
            ZahtevMaterijalaDto dto = convertToDto(zahtev);
            dtoList.add(dto);
        }

        return ResponseEntity.ok(dtoList);
    }

    @SneakyThrows
    @GetMapping("/all")
    public ResponseEntity<List<ZahtevMaterijalaDto>> getAllRequests() {
        List<ZahtevMaterijala> requests = trebovanjeRepository.findAll();
        List<ZahtevMaterijalaDto> dtoList = new ArrayList<>();
        for (ZahtevMaterijala zahtev : requests) {
            ZahtevMaterijalaDto dto = convertToDto(zahtev);
            dtoList.add(dto);
        }

        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<ZahtevMaterijalaDto> updateStatus(@PathVariable Long id, @RequestParam String status, @RequestParam Long adminId) throws IllegalAccessException, InstantiationException {
        ZahtevMaterijala trebovanje = trebovanjeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Fetch the admin who is updating the status
        Administrator admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        trebovanje.setStatus(status);
        trebovanje.setAdmin(admin);
        trebovanje.setDatumIzmena(new Timestamp(System.currentTimeMillis()));

        ZahtevMaterijala updatedTrebovanje = trebovanjeRepository.save(trebovanje);
        return ResponseEntity.ok(convertToDto(updatedTrebovanje));
    }
}
