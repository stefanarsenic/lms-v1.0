package rs.ac.singidunum.novisad.server.model.obavestenje;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class GoogleDriveService {

    @Autowired
    private Drive drive;

    public String uploadFile(MultipartFile file) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());

        InputStream inputStream = file.getInputStream();
        File uploadedFile = drive.files().create(fileMetadata, new InputStreamContent(file.getContentType(), inputStream))
                .setFields("id")
                .execute();

        return uploadedFile.getId();
    }

    public FileList listFiles() throws IOException {
        return drive.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
    }

    public byte[] downloadFile(String fileId) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        drive.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
        return outputStream.toByteArray();
    }

    public File getFileMetadata(String fileId) throws IOException {
        return drive.files().get(fileId).execute();
    }

}
