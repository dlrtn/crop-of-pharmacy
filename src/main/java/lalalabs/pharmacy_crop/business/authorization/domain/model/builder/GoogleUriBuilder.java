package lalalabs.pharmacy_crop.business.authorization.domain.model.builder;

import lalalabs.pharmacy_crop.business.authorization.domain.model.GoogleOauthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleUriBuilder {

    private final GoogleOauthProperties properties;

    public String buildGetMemberUri() {
        return UriComponentsBuilder.fromUriString(properties.getUserInfoUri())
                .toUriString();
    }

    public String buildUnlinkUri(String accessToken) {
        return UriComponentsBuilder.fromUriString(properties.getUnlinkUri())
                .queryParam("token", accessToken)
                .toUriString();
    }
}
