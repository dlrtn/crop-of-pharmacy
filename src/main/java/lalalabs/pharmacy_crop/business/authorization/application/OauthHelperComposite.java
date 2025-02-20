package lalalabs.pharmacy_crop.business.authorization.application;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lalalabs.pharmacy_crop.business.authorization.application.usecase.OauthHelper;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import org.springframework.stereotype.Component;

@Component
public class OauthHelperComposite {

    private final Map<OauthServiceType, OauthHelper> mapping;

    public OauthHelperComposite(Set<OauthHelper> helpers) {
        mapping = helpers.stream()
                .collect(toMap(
                        OauthHelper::supportServer,
                        identity()
                ));
    }

    public OIDCDecodePayload decode(OauthServiceType serviceType, OauthTokenDto oauthTokenDto) {
        return getHelper(serviceType).decode(oauthTokenDto);
    }

    public OauthHelper getHelper(OauthServiceType serviceType) {
        return Optional.ofNullable(mapping.get(serviceType))
                .orElseThrow(() -> new RuntimeException("Unsupported OauthServiceType: " + serviceType));
    }

    public OauthUserInfoDto fetchUserInfo(OauthServiceType serviceType, OauthTokenDto accessToken) {
        return getHelper(serviceType).fetchUserInfo(accessToken);
    }

    public void unlink(OauthUser user, OauthTokenDto oauthTokenDto) {
        getHelper(user.getOauthId().getOauthServiceType()).unlink(user.getId(), oauthTokenDto);
    }
}
