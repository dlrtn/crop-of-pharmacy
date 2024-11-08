package lalalabs.pharmacy_crop.business.authorization.domain.kakao;

import java.util.Objects;
import lalalabs.pharmacy_crop.business.authorization.domain.OauthHelper;
import lalalabs.pharmacy_crop.business.authorization.domain.OauthOIDCHelper;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthTokenEntity;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.client.KakaoApiClient;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.KakaoTokenDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.KakaoUnlinkResponse;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OIDCPublicKeysResponse;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.repository.OauthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KakaoOauthHelper implements OauthHelper {

    private final KakaoApiClient kakaoOauthClient;
    private final KakaoOauthProperties oauthProperties;
    private final OauthOIDCHelper oauthOIDCHelper;
    private final OauthTokenRepository oauthTokenRepository;

    public OauthServiceType supportServer() {
        return OauthServiceType.KAKAO;
    }

    public KakaoTokenDto fetchToken(String code) {
        return kakaoOauthClient.fetchToken(code);
    }

    public OauthUserInfoDto fetchUserInfo(OauthTokenDto oauthTokenDto) {
        OauthUserInfoDto oauthUserInfoDto = kakaoOauthClient.fetchUserInfo(oauthTokenDto.getAccessToken());

        oauthTokenRepository.save(oauthTokenDto.toEntity(oauthUserInfoDto.id()));

        return oauthUserInfoDto;
    }

    public void unlink(String oauthId) {
        OauthTokenEntity oauthTokenEntity = oauthTokenRepository.findById(oauthId)
                .orElseThrow(() -> new RuntimeException("Failed to find oauth token by oauthId: " + oauthId));

        KakaoUnlinkResponse response = kakaoOauthClient.unlink(oauthTokenEntity.getAccessToken());
        if (!Objects.equals(response.id(), oauthId)) {
            throw new RuntimeException("Failed to unlink user from oauth server");
        }

        oauthTokenRepository.delete(oauthTokenEntity);
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
