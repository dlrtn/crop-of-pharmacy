package lalalabs.pharmacy_crop.business.authorization.domain.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleOauthProperties {

    @Value("${authorization.oauth.google.uri.authorize-code}")
    private String authorizeCodeUri;

    @Value("${authorization.oauth.google.uri.oauth-token}")
    private String googleOauthTokenUri;

    @Value("${authorization.oauth.google.uri.redirect}")
    private String redirectUri;

    @Value("${authorization.oauth.google.uri.user-info}")
    private String userInfoUri;

    @Value("${authorization.oauth.google.uri.unlink}")
    private String unlinkUri;
}
