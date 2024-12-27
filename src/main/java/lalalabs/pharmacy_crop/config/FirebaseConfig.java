package lalalabs.pharmacy_crop.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
@Slf4j
public class FirebaseConfig {

    @SneakyThrows
    public FirebaseConfig() {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/angular-expanse-443108-v8-e1c80a2cf9a2.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        log.info("FirebaseApp initialized");
    }
}
