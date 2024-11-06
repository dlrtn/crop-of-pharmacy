package lalalabs.pharmacy_crop.business.authorization.domain.google;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GoogleUriBuilder {
    @Value("${uri.authorization.google.oauth.authorize}")
    private String googleOauthAuthorizeUri;

    @Value("${key.authorization.google.client-id}")
    private String clientId;

    @Value("${key.authorization.google.client-secret}")
    private String clientSecret;

    @Value("${uri.authorization.google.redirect-uri}")
    private String redirectUri;

    @Value("${uri.authorization.google.user-info}")
    private String userInfoUri;

    @Value("${uri.authorization.google.oauth.token}")
    private String tokenUri;

    public String buildAuthorizeUri() {
        return UriComponentsBuilder.fromUriString(googleOauthAuthorizeUri)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", "https://www.googleapis.com/auth/userinfo.profile")
                .toUriString();
    }

    public String buildGetTokenUri(String code) {
        return UriComponentsBuilder.fromUriString(tokenUri)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("code", code)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("client_secret", clientSecret)
                .toUriString();
    }

    public String buildGetUserInfoUri() {
        return UriComponentsBuilder.fromUriString(userInfoUri)
                .toUriString();
    }
}
