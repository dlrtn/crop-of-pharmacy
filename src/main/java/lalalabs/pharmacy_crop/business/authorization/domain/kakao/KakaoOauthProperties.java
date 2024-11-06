package lalalabs.pharmacy_crop.business.authorization.domain.kakao;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KakaoOauthProperties {

    @Value("${key.authorization.kakao.rest-api}")
    private String clientId;

    @Value("${key.authorization.kakao.client-secret}")
    private String clientSecret;

    @Value("${uri.authorization.kakao.oauth.authorize}")
    private String kakaoOauthAuthorizeUri;

    @Value("${uri.authorization.kakao.oauth.token}")
    private String kakaoOauthTokenUri;


    @Value("${uri.authorization.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${uri.authorization.kakao.user-info}")
    private String userInfoUri;

    @Value("${uri.authorization.kakao.oauth.oidc-public-key}")
    private String oidcPublicKeyUri;

    private String baseUrl = "https://kapi.kakao.com";

}
