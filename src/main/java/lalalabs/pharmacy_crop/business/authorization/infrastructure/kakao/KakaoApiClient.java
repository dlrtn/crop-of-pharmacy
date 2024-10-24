package lalalabs.pharmacy_crop.business.authorization.infrastructure.kakao;

import lalalabs.pharmacy_crop.business.authorization.domain.kakao.KakaoMemberResponse;
import lalalabs.pharmacy_crop.business.authorization.domain.kakao.KakaoToken;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthUser;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.usecase.OauthServiceClient;
import lalalabs.pharmacy_crop.common.ApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OauthServiceClient {
    private final ApiClient apiClient;
    private final KakaoUriBuilder kakaoUriBuilder;

    private KakaoToken fetchToken(String code) {
        String uri = kakaoUriBuilder.buildGetTokenUri(code);

        return apiClient.post(uri, KakaoToken.class);
    }

    @Override
    public OauthServiceType supportServer() {
        return OauthServiceType.KAKAO;
    }

    public OauthUser fetch(String code) {
        KakaoToken token = fetchToken(code);

        KakaoMemberResponse kakaoMemberResponse = apiClient.get(kakaoUriBuilder.buildGetMemberUri(),
                KakaoMemberResponse.class, token.accessToken());

        return kakaoMemberResponse.toDomain();
    }
}