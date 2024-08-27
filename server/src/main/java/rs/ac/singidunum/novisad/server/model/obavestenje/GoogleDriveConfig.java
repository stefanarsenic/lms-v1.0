package rs.ac.singidunum.novisad.server.model.obavestenje;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleDriveConfig {

    private static final String APPLICATION_NAME = "lms-projekat-2024";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Bean
    public Drive googleDrive() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        Credential credential = getCredentials(httpTransport);

        return new Drive.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential getCredentials(HttpTransport httpTransport) throws IOException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("C:\\Users\\stefa\\Desktop\\lms_projekat\\learning-management-system\\server\\src\\main\\java\\rs\\ac\\singidunum\\novisad\\server\\model\\obavestenje\\client_secret_1010100917967-235ij32pnd2mlds02reg93soadn252im.apps.googleusercontent.com.json"))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return credential;
    }
}

