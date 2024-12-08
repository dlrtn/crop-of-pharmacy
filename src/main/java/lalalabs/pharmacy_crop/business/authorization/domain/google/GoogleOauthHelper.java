package lalalabs.pharmacy_crop.business.authorization.domain.google;

import static com.google.api.client.util.Data.isNull;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lalalabs.pharmacy_crop.business.authorization.application.usecase.OauthHelper;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.domain.model.entity.OauthTokenEntity;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.client.GoogleApiClient;
import lalalabs.pharmacy_crop.business.authorization.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.repository.OauthTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GoogleOauthHelper implements OauthHelper {

    private final GoogleApiClient googleApiClient;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final OauthTokenRepository oauthTokenRepository;

    public OauthServiceType supportServer() {
        return OauthServiceType.GOOGLE;
    }

    public OauthUserInfoDto fetchUserInfo(OauthTokenDto oauthTokenDto) {
        return googleApiClient.fetchUserInfo(oauthTokenDto);
    }

    public void unlink(String oauthId) {
        OauthTokenEntity oauthTokenEntity = oauthTokenRepository.findById(oauthId)
                .orElseThrow(() -> new RuntimeException("Failed to find oauth token by oauthId: " + oauthId));

        googleApiClient.unlink(oauthTokenEntity.getAccessToken());
        oauthTokenRepository.delete(oauthTokenEntity);
    }

    public OIDCDecodePayload decode(OauthTokenDto googleToken) {
        try {
            GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(googleToken.getIdToken());

            log.info("googleIdToken: {}", googleIdToken);

            if (isNull(googleIdToken)) {
                throw new RuntimeException("Invalid id token");
            }

            return new OIDCDecodePayload(googleIdToken.getPayload().getSubject(),
                    googleIdToken.getPayload().getIssuer(), googleIdToken.getHeader().getKeyId());
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Failed to verify id token", e);
        }
    }
}
