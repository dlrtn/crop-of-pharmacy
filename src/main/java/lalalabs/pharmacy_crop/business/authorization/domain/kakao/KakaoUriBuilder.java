package lalalabs.pharmacy_crop.business.authorization.domain.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoUriBuilder {

    private final KakaoOauthProperties properties;

    public String buildAuthorizeUri() {
        return UriComponentsBuilder.fromUriString(properties.getAuthorizeCodeUri())
                .queryParam("response_type", "code")
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", properties.getRedirectUri())
                .toUriString();
    }

    public String buildGetTokenUri(String code) {
        return UriComponentsBuilder.fromUriString(properties.getKakaoOauthTokenUri())
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", properties.getRedirectUri())
                .queryParam("code", code)
                .queryParam("client_secret", properties.getClientSecret())
                .toUriString();
    }

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