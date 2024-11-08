package lalalabs.pharmacy_crop.business.authorization.domain.google;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleUriBuilder {

    private final GoogleOauthProperties properties;

    public String buildAuthorizeUri() {
        return UriComponentsBuilder.fromUriString(properties.getAuthorizeCodeUri())
                .queryParam("response_type", "code")
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", properties.getRedirectUri())
                .queryParam("scope", "https://www.googleapis.com/auth/userinfo.profile")
                .toUriString();
    }

    public String buildGetTokenUri(String code) {
        return UriComponentsBuilder.fromUriString(properties.getGoogleOauthTokenUri())
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", properties.getClientId())
                .queryParam("code", code)
                .queryParam("redirect_uri", properties.getRedirectUri())
                .queryParam("client_secret", properties.getClientSecret())
                .toUriString();
    }

    public String buildGetMemberUri() {
        return UriComponentsBuilder.fromUriString(properties.getUserInfoUri())
                .toUriString();
    }
}
