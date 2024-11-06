package lalalabs.pharmacy_crop.business.authorization.application;

import lalalabs.pharmacy_crop.business.authorization.domain.common.AuthorizationCodeRequestUriProviderComposite;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthUser;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.OauthServiceClientComposite;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final AuthorizationCodeRequestUriProviderComposite uriProviderComposite;
    private final OauthServiceClientComposite oauthServiceClientComposite;

    public String getAuthorizationCodeRequestUri(OauthServiceType serviceType) {
        return uriProviderComposite.provide(serviceType);
    }

    public void login(OauthServiceType serviceType, String code) {
        OauthUser member = oauthServiceClientComposite.fetch(serviceType, code);
        OIDCPublicKeysResponse oidcPublicKeysResponse = oauthServiceClientComposite.getOIDCPublicKey(serviceType);

        oidcPublicKeysResponse.print();

        // save member
    }
}
