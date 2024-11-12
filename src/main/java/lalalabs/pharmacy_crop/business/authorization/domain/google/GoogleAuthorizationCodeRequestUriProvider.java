package lalalabs.pharmacy_crop.business.authorization.domain.google;

import lalalabs.pharmacy_crop.business.authorization.application.usecase.AuthorizationCodeRequestUriProvider;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleAuthorizationCodeRequestUriProvider implements AuthorizationCodeRequestUriProvider {

    private final GoogleUriBuilder googleUriBuilder;

    @Override
    public OauthServiceType supportServer() {
        return OauthServiceType.GOOGLE;
    }

    @Override
    public String provide() {
        return googleUriBuilder.buildAuthorizeUri();
    }
}
