package lalalabs.pharmacy_crop.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lalalabs.pharmacy_crop.common.push_notification.domain.FirebaseProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class FirebaseConfig {

    private final FirebaseProperties firebaseProperties;

    @javax.annotation.PostConstruct
    public void initializeFirebaseApp() {
        try {
            // 환경 변수 또는 구성 파일로부터 JSON 문자열 가져오기
            String firebaseConfigJson = firebaseProperties.getJsonConfig();
            if (firebaseConfigJson == null || firebaseConfigJson.isEmpty()) {
                throw new IllegalStateException("Firebase configuration JSON is missing or empty");
            }

            // Firebase 초기화
            ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseConfigJson.getBytes());
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            log.info("FirebaseApp successfully initialized with provided configuration.");
        } catch (Exception e) {
            log.error("Failed to initialize FirebaseApp: {}", e.getMessage(), e);
            throw new IllegalStateException("Could not initialize FirebaseApp", e);
        }
    }
}
