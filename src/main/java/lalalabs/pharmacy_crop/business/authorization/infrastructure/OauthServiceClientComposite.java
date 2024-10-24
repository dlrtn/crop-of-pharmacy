package lalalabs.pharmacy_crop.business.authorization.infrastructure;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthUser;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.usecase.OauthServiceClient;
import org.springframework.stereotype.Component;

@Component
public class OauthServiceClientComposite {

    private final Map<OauthServiceType, OauthServiceClient> mapping;

    public OauthServiceClientComposite(Set<OauthServiceClient> clients) {
        mapping = clients.stream()
                .collect(toMap(
                        OauthServiceClient::supportServer,
                        identity()
                ));
    }

    public OauthUser fetch(OauthServiceType oauthServiceType, String authCode) {
        return getClient(oauthServiceType).fetch(authCode);
    }

    private OauthServiceClient getClient(OauthServiceType oauthServiceType) {
        return Optional.ofNullable(mapping.get(oauthServiceType))
                .orElseThrow(() -> new RuntimeException("Unsupported oauthServiceType: " + oauthServiceType));
    }
}
