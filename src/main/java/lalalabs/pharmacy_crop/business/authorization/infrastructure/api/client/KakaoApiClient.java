package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.client;

import lalalabs.pharmacy_crop.business.authorization.domain.model.builder.KakaoUriBuilder;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.KakaoMemberResponse;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.KakaoUnlinkResponse;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OIDCPublicKeysResponse;
import lalalabs.pharmacy_crop.common.ApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoApiClient {

    private final ApiClient apiClient;
    private final KakaoUriBuilder kakaoUriBuilder;

    public OauthUserInfoDto fetchUserInfo(String accessToken) {
        KakaoMemberResponse kakaoMemberResponse = apiClient.get(kakaoUriBuilder.buildGetMemberUri(),
                KakaoMemberResponse.class, accessToken);

        return kakaoMemberResponse.toOauthUserInfoDto();
    }

    @Cacheable(cacheNames = "kakao-oidc-public-key", cacheManager = "oidcCacheManager")
    public OIDCPublicKeysResponse fetchOIDCPublicKey() {
        return apiClient.get(kakaoUriBuilder.buildGetOIDCPublicKeyUri(), OIDCPublicKeysResponse.class);
    }

    public KakaoUnlinkResponse unlink(String accessToken) {
        return apiClient.post(kakaoUriBuilder.buildUnlinkUri(), accessToken, KakaoUnlinkResponse.class);
    }
}
