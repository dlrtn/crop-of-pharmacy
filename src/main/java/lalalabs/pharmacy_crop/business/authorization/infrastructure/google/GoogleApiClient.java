package lalalabs.pharmacy_crop.business.authorization.infrastructure.google;

import lalalabs.pharmacy_crop.business.authorization.domain.google.GoogleMemberResponse;
import lalalabs.pharmacy_crop.business.authorization.domain.google.GoogleToken;
import lalalabs.pharmacy_crop.business.authorization.domain.google.GoogleUriBuilder;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthUser;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.dto.OIDCPublicKeysResponse;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.usecase.OauthServiceClient;
import lalalabs.pharmacy_crop.common.ApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleApiClient implements OauthServiceClient {
    private final ApiClient apiClient;
    private final GoogleUriBuilder googleUriBuilder;

    private GoogleToken fetchToken(String code) {
        String uri = googleUriBuilder.buildGetTokenUri(code);

        return apiClient.post(uri, GoogleToken.class);
    }

    @Override
    public OauthServiceType supportServer() {
        return OauthServiceType.GOOGLE;
    }

    public OauthUser fetch(String code) {
        GoogleToken token = fetchToken(code);

        GoogleMemberResponse googleMemberResponse = apiClient.get(googleUriBuilder.buildGetUserInfoUri(),
                GoogleMemberResponse.class, token.accessToken());

        return googleMemberResponse.toDomain();
    }

    @Override
    public OIDCPublicKeysResponse getOIDCPublicKey() {
        return null;
    }
}