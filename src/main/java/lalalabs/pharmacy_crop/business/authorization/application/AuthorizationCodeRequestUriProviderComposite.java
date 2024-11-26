package lalalabs.pharmacy_crop.business.authorization.application;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lalalabs.pharmacy_crop.business.authorization.application.usecase.AuthorizationCodeRequestUriProvider;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationCodeRequestUriProviderComposite {

    private final Map<OauthServiceType, AuthorizationCodeRequestUriProvider> mapping;

    public AuthorizationCodeRequestUriProviderComposite(Set<AuthorizationCodeRequestUriProvider> providers) {
        mapping = providers.stream()
                .collect(toMap(
                        AuthorizationCodeRequestUriProvider::supportServer,
                        identity()
                ));
    }

    public String provide(OauthServiceType serviceType) {
        return getProvider(serviceType).provide();
    }

    private AuthorizationCodeRequestUriProvider getProvider(OauthServiceType serviceType) {
        return Optional.ofNullable(mapping.get(serviceType))
                .orElseThrow(() -> new RuntimeException("Unsupported OauthServiceType: " + serviceType));
    }
}
