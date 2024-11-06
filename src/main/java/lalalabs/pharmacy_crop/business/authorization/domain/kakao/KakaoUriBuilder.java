package lalalabs.pharmacy_crop.business.authorization.domain.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KakaoUriBuilder {

    @Value("${key.authorization.kakao.rest-api}")
    private String kakaoRestApiKey;

    @Value("${uri.authorization.kakao.oauth.authorize}")
    private String kakaoOauthAuthorizeUri;

    @Value("${uri.authorization.kakao.oauth.token}")
    private String kakaoOauthTokenUri;

    @Value("${key.authorization.kakao.client-secret}")
    private String clientSecret;

    @Value("${uri.authorization.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${uri.authorization.kakao.user-info}")
    private String userInfoUri;

    public String buildAuthorizeUri() {
        return UriComponentsBuilder.fromUriString(kakaoOauthAuthorizeUri)
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoRestApiKey)
                .queryParam("redirect_uri", redirectUri)
                .toUriString();
    }

    public String buildGetTokenUri(String code) {
        return UriComponentsBuilder.fromUriString(kakaoOauthTokenUri)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", kakaoRestApiKey)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .queryParam("client_secret", clientSecret)
                .toUriString();
    }

    public String buildGetMemberUri() {
        return UriComponentsBuilder.fromUriString(userInfoUri)
                .toUriString();
    }
}