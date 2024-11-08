package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.client;

import lalalabs.pharmacy_crop.business.authorization.domain.google.GoogleMemberResponse;
import lalalabs.pharmacy_crop.business.authorization.domain.google.GoogleTokenDto;
import lalalabs.pharmacy_crop.business.authorization.domain.google.GoogleUriBuilder;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.common.ApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleApiClient {

    private final ApiClient apiClient;
    private final GoogleUriBuilder googleUriBuilder;

    public GoogleTokenDto fetchToken(String code) {
        String uri = googleUriBuilder.buildGetTokenUri(code);

        return apiClient.post(uri, GoogleTokenDto.class);
    }

    public OauthUserInfoDto fetchUserInfo(OauthTokenDto oauthTokenDto) {
        GoogleMemberResponse googleMemberResponse = apiClient.get(googleUriBuilder.buildGetMemberUri(),
                GoogleMemberResponse.class, oauthTokenDto.getAccessToken());

        return googleMemberResponse.toOauthUserInfoDto();
    }

    public void unlink(String oauthId) {

    }
}
