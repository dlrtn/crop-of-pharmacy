package lalalabs.pharmacy_crop.business.authorization.domain.google;

import static com.google.api.client.util.Data.isNull;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lalalabs.pharmacy_crop.business.authorization.domain.OauthHelper;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.client.GoogleApiClient;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.GoogleTokenDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.repository.OauthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GoogleOauthHelper implements OauthHelper {

    private final GoogleApiClient googleApiClient;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final OauthTokenRepository oauthTokenRepository;

    public OauthServiceType supportServer() {
        return OauthServiceType.GOOGLE;
    }

    public GoogleTokenDto fetchToken(String code) {
        return googleApiClient.fetchToken(code);
    }

    public OauthUserInfoDto fetchUserInfo(OauthTokenDto oauthTokenDto) {
        OauthUserInfoDto oauthUserInfoDto = googleApiClient.fetchUserInfo(oauthTokenDto);

        oauthTokenRepository.save(oauthTokenDto.toEntity(oauthUserInfoDto.id()));

        return oauthUserInfoDto;
    }

    public void unlink(String oauthId) {
        googleApiClient.unlink(oauthId);
    }

    public OIDCDecodePayload decode(OauthTokenDto googleToken) {
        try {
            GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(googleToken.getIdToken());

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
