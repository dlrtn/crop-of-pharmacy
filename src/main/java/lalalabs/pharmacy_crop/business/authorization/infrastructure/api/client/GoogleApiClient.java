package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.client;

import lalalabs.pharmacy_crop.business.authorization.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.authorization.domain.model.builder.GoogleUriBuilder;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.GoogleMemberResponse;
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

    public OauthUserInfoDto fetchUserInfo(OauthTokenDto oauthTokenDto) {
        GoogleMemberResponse googleMemberResponse = apiClient.get(googleUriBuilder.buildGetMemberUri(),
                GoogleMemberResponse.class, oauthTokenDto.getAccessToken());

        return googleMemberResponse.toOauthUserInfoDto();
    }

    public void unlink(String accessToken) {
        apiClient.get(googleUriBuilder.buildUnlinkUri(accessToken), String.class);
    }
}
