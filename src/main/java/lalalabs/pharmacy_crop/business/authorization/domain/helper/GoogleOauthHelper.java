package lalalabs.pharmacy_crop.business.authorization.domain.helper;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lalalabs.pharmacy_crop.business.authorization.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.authorization.application.usecase.OauthHelper;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.domain.model.exception.InvalidGoogleIdTokenException;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.client.GoogleApiClient;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.repository.OauthTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.google.api.client.util.Data.isNull;

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

    public void unlink(String userId, OauthTokenDto oauthTokenDto) {
        googleApiClient.unlink(oauthTokenDto.getAccessToken());
        oauthTokenRepository.deleteById(userId);
    }

    @SneakyThrows
    public OIDCDecodePayload decode(OauthTokenDto googleToken) {
        GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(googleToken.getIdToken());

        if (isNull(googleIdToken)) {
            throw new InvalidGoogleIdTokenException();
        }

        return OIDCDecodePayload.fromGoogleIdToken(googleIdToken);
    }
}
