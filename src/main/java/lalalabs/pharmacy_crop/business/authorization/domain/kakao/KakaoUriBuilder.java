package lalalabs.pharmacy_crop.business.authorization.domain.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoUriBuilder {

    private final KakaoOauthProperties properties;

    public String buildGetOIDCPublicKeyUri() {
        return UriComponentsBuilder.fromUriString(properties.getOidcPublicKeyUri())
                .toUriString();
    }

    public String buildGetMemberUri() {
        return UriComponentsBuilder.fromUriString(properties.getUserInfoUri())
                .toUriString();
    }

    public String buildUnlinkUri() {
        return UriComponentsBuilder.fromUriString(properties.getUnlinkUri())
                .toUriString();
    }
}