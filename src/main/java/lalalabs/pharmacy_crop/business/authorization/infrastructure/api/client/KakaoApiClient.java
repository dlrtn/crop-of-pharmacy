package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.client;

import lalalabs.pharmacy_crop.business.authorization.domain.kakao.KakaoMemberResponse;
import lalalabs.pharmacy_crop.business.authorization.domain.kakao.KakaoOauthProperties;
import lalalabs.pharmacy_crop.business.authorization.domain.kakao.KakaoTokenDto;
import lalalabs.pharmacy_crop.business.authorization.domain.kakao.KakaoUriBuilder;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
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
    private final KakaoOauthProperties properties;

    public KakaoTokenDto fetchToken(String code) {
        String uri = kakaoUriBuilder.buildGetTokenUri(code);

        return apiClient.post(uri, KakaoTokenDto.class);
    }

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
