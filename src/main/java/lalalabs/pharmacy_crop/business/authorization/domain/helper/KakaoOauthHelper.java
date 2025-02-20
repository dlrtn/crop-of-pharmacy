package lalalabs.pharmacy_crop.business.authorization.domain.helper;

import lalalabs.pharmacy_crop.business.authorization.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.authorization.application.usecase.OauthHelper;
import lalalabs.pharmacy_crop.business.authorization.domain.OauthOIDCHelper;
import lalalabs.pharmacy_crop.business.authorization.domain.model.KakaoOauthProperties;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.domain.model.exception.KakaoUnlinkFailedException;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.client.KakaoApiClient;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.KakaoUnlinkResponse;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class KakaoOauthHelper implements OauthHelper {

    private final KakaoApiClient kakaoOauthClient;
    private final KakaoOauthProperties oauthProperties;
    private final OauthOIDCHelper oauthOIDCHelper;

    public OauthServiceType supportServer() {
        return OauthServiceType.KAKAO;
    }

    public OauthUserInfoDto fetchUserInfo(OauthTokenDto oauthTokenDto) {
        return kakaoOauthClient.fetchUserInfo(oauthTokenDto.getAccessToken());
    }

    public void unlink(String userId, OauthTokenDto oauthTokenDto) {
        KakaoUnlinkResponse response = kakaoOauthClient.unlink(oauthTokenDto.getAccessToken());

        if (!Objects.equals(response.id(), userId)) {
            throw new KakaoUnlinkFailedException(userId);
        }
    }

    public OIDCDecodePayload decode(OauthTokenDto kakaoToken) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.fetchOIDCPublicKey();

        return oauthOIDCHelper.getPayloadFromIdToken(
                kakaoToken.getIdToken(),
                oauthProperties.getBaseUrl(),
                oauthProperties.getClientId(),
                oidcPublicKeysResponse);
    }
}
