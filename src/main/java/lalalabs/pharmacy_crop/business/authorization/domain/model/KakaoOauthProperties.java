package lalalabs.pharmacy_crop.business.authorization.domain.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KakaoOauthProperties {

    @Value("${authorization.oauth.kakao.key.client-id}")
    private String clientId;

    @Value("${authorization.oauth.kakao.key.client-secret}")
    private String clientSecret;

    @Value("${authorization.oauth.kakao.key.admin}")
    private String adminKey;

    @Value("${authorization.oauth.kakao.uri.authorize-code}")
    private String authorizeCodeUri;

    @Value("${authorization.oauth.kakao.uri.oauth-token}")
    private String kakaoOauthTokenUri;

    @Value("${authorization.oauth.kakao.uri.oidc-public-key}")
    private String oidcPublicKeyUri;

    @Value("${authorization.oauth.kakao.uri.redirect}")
    private String redirectUri;

    @Value("${authorization.oauth.kakao.uri.user-info}")
    private String userInfoUri;

    @Value("${authorization.oauth.kakao.uri.base}")
    private String baseUrl;

    @Value("${authorization.oauth.kakao.uri.unlink}")
    private String unlinkUri;
}
