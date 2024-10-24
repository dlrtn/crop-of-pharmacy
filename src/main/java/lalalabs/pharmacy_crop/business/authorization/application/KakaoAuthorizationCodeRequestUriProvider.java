package lalalabs.pharmacy_crop.business.authorization.application;

import lalalabs.pharmacy_crop.business.authorization.application.usecase.AuthorizationCodeRequestUriProvider;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.kakao.KakaoUriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthorizationCodeRequestUriProvider implements AuthorizationCodeRequestUriProvider {

    private final KakaoUriBuilder kakaoUriBuilder;

    @Override
    public OauthServiceType supportServer() {
        return OauthServiceType.KAKAO;
    }

    @Override
    public String provide() {
        return kakaoUriBuilder.buildAuthorizeUri();
    }
}
