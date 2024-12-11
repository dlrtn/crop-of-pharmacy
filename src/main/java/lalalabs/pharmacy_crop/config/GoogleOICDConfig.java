package lalalabs.pharmacy_crop.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lalalabs.pharmacy_crop.business.authorization.domain.google.GoogleOauthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GoogleOICDConfig {

    private final GoogleOauthProperties googleOauthProperties;

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier() {
        List<String> audience = new ArrayList<>();
        audience.add(googleOauthProperties.getClientId());
        audience.add(googleOauthProperties.getAndroidClientId());
        audience.add(googleOauthProperties.getIosClientId());

        return new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(audience)
                .build();
    }
}
