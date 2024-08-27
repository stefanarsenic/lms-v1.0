package rs.ac.singidunum.novisad.server.model.obavestenje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.singidunum.novisad.server.repositories.obavestenje.FajlRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/drive")
public class GoogleDriveController {

    @Autowired
    private GoogleDriveService googleDriveService;
    @Autowired
    private FajlRepository fajlRepository;

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) {
        try {
            byte[] fileContent = googleDriveService.downloadFile(fileId);

            com.google.api.services.drive.model.File fileMetadata = googleDriveService.getFileMetadata(fileId);

            String fileName = fileMetadata.getName();
            String mimeType = fileMetadata.getMimeType();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentDispositionFormData("attachment", fileName);

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileId = googleDriveService.uploadFile(file);
        Fajl fajl = new Fajl();
        fajl.setSifra(fileId);
        fajlRepository.save(fajl);
        return ResponseEntity.ok(fajl);
    }

    @GetMapping("/files")
    public ResponseEntity<?> listFiles() throws IOException {
        return ResponseEntity.ok(googleDriveService.listFiles().getFiles());
    }
}
