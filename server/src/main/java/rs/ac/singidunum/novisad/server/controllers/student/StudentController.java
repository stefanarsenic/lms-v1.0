package rs.ac.singidunum.novisad.server.controllers.student;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.novisad.server.dto.adresa.AdresaDto;
import rs.ac.singidunum.novisad.server.dto.adresa.MestoDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentDto;
import rs.ac.singidunum.novisad.server.dto.student.StudentInfoDTO;
import rs.ac.singidunum.novisad.server.generic.EntityDtoMapper;
import rs.ac.singidunum.novisad.server.generic.GenericController;
import rs.ac.singidunum.novisad.server.generic.GenericService;
import rs.ac.singidunum.novisad.server.model.adresa.Adresa;
import rs.ac.singidunum.novisad.server.model.adresa.Mesto;
import rs.ac.singidunum.novisad.server.model.student.Student;
import rs.ac.singidunum.novisad.server.services.adresa.AdresaService;
import rs.ac.singidunum.novisad.server.services.student.StudentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController extends GenericController<Student, Long, StudentDto> {
    private final AdresaService adresaService;
    private final StudentService studentService;
    public StudentController(GenericService<Student, Long> service, AdresaService adresaService, StudentService studentService) {
        super(service);
        this.adresaService = adresaService;
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}/drzava")
    public ResponseEntity<String> getNazivDrzaveByStudentId(@PathVariable Long studentId){
        String drzava = studentService.findNazivDrzaveByStudentId(studentId);

        return new ResponseEntity<>(drzava, HttpStatus.OK);
    }

    @Override
    protected StudentDto convertToDto(Student entity) throws IllegalAccessException, InstantiationException {
        StudentDto studentDto = EntityDtoMapper.convertToDto(entity, StudentDto.class);
        AdresaDto adresaDto = EntityDtoMapper.convertToDto(entity.getAdresa(), AdresaDto.class);
        MestoDto mestoDto=EntityDtoMapper.convertToDto(entity.getAdresa().getMesto(),MestoDto.class);
        adresaDto.setMesto(mestoDto);
        studentDto.setAdresa(adresaDto);

        return studentDto;
    }

    @Override
    protected Student convertToEntity(StudentDto dto) throws IllegalAccessException, InstantiationException {
        Adresa adresa = adresaService.findById(dto.getAdresa().getId()).orElseThrow();

        Student student = EntityDtoMapper.convertToEntity(dto, Student.class);
        student.setAdresa(adresa);

        return student;
    }

    @Override
    @RequestMapping(path = "/dodaj",method = RequestMethod.POST)
    public ResponseEntity<StudentDto> create(StudentDto dto) throws IllegalAccessException, InstantiationException {
        Mesto mesto=EntityDtoMapper.convertToEntity(dto.getAdresa().getMesto(),Mesto.class);
        Adresa adresa=EntityDtoMapper.convertToEntity(dto.getAdresa(),Adresa.class);
        adresa.setMesto(mesto);
        Adresa adresa1= adresaService.save(adresa);
        AdresaDto adresaDto=EntityDtoMapper.convertToDto(adresa1,AdresaDto.class);
        dto.setAdresa(adresaDto);
        return super.create(dto);
    }

    @GetMapping("/{id}/info")
    public List<Map<String, Object>> getStudentInfo(@PathVariable("id") Long studentId) {
        List<Object[]> results = studentService.getStudentInfo(studentId);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> mappedRow = new HashMap<>();
            mappedRow.put("studentId", row[0]);
            mappedRow.put("ime", row[1]);
            mappedRow.put("prezime", row[2]);
            mappedRow.put("jmbg", row[3]);
            mappedRow.put("datumRodjenja", row[4]);
            mappedRow.put("ulica", row[5]);
            mappedRow.put("broj", row[6]);
            mappedRow.put("mesto", row[7]);
            mappedRow.put("drzava", row[8]);
            mappedRow.put("godinaStudija", row[9]);
            mappedRow.put("datumUpisa", row[10]);
            mappedRow.put("espbOsvojeno", row[11]);
            mappedRow.put("espbNajava", row[12]);
            mappedRow.put("studijskiProgram", row[13]);
            mappedRow.put("pocetakSkolskeGodine", row[14]);
            mappedRow.put("krajSkolskeGodine", row[15]);
            mappedRow.put("predmet", row[16]);
            mappedRow.put("konacnaOcena", row[17]);
            mappedRow.put("bodoviNaPolaganju", row[18]);
            mappedRow.put("pocetakEvaluacije", row[19]);
            mappedRow.put("zavrsetakEvaluacije", row[20]);
            mappedRow.put("ispitniRok", row[21]);
            mappedRow.put("statusPolaganja", row[22]);
            mappedRow.put("napomenaNaPolaganju", row[23]);
            mappedRow.put("prosjecnaOcena", row[24]);
            response.add(mappedRow);
        }

        return response;
    }
}
