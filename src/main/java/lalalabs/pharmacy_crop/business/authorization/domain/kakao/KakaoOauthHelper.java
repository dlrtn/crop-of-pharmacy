package lalalabs.pharmacy_crop.business.authorization.domain.kakao;

import lalalabs.pharmacy_crop.business.authorization.domain.kakao.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.OauthOIDCHelper;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.dto.OIDCPublicKeysResponse;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.kakao.KakaoApiClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoOauthHelper {

    private final KakaoApiClient kakaoOauthClient;
    private final KakaoOauthProperties oauthProperties;
    private final OauthOIDCHelper oauthOIDCHelper;

    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        // 공개키 목록을 조회한다. 캐싱이 되어있다.
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.getOIDCPublicKey();
        return oauthOIDCHelper.getPayloadFromIdToken(
                //idToken
                token,
                // iss 와 대응되는 값
                oauthProperties.getBaseUrl(),
                // aud 와 대응되는값
                oauthProperties.getClientId(),
                // 공개키 목록
                oidcPublicKeysResponse);
    }
}
