package lalalabs.pharmacy_crop.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lalalabs.pharmacy_crop.business.authorization.domain.model.GoogleClientIdResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GoogleOICDConfig {

    private final GoogleClientIdResolver googleClientIdResolver;

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier() {
        List<String> audience = googleClientIdResolver.getClientIds();

        return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory()).setAudience(audience).build();
    }
}
