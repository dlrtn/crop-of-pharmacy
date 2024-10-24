package lalalabs.pharmacy_crop.business.authorization.application;

import lalalabs.pharmacy_crop.business.authorization.domain.AuthorizationCodeRequestUriProviderComposite;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthUser;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.OauthServiceClientComposite;
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

        System.out.println(member.id());
        System.out.println(member.oauthId().oauthServerId());
        System.out.println(member.oauthId().oauthServer());
        System.out.println(member.nickname());

        // save member
    }
}
