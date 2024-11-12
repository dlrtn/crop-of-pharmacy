package lalalabs.pharmacy_crop.business.authorization.domain.kakao;

import lalalabs.pharmacy_crop.business.authorization.application.usecase.AuthorizationCodeRequestUriProvider;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoAuthorizationCodeRequestUriProvider implements AuthorizationCodeRequestUriProvider {

    private final KakaoUriBuilder kakaoUriBuilder;

    public OauthServiceType supportServer() {
        return OauthServiceType.KAKAO;
    }

    public String provide() {
        return kakaoUriBuilder.buildAuthorizeUri();
    }
}
