package lalalabs.pharmacy_crop.business.authorization.domain.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoToken(
        String tokenType,
        String accessToken,
        String idToken,
        String refreshToken,
        Integer expiresIn,
        Integer refreshTokenExpiresIn,
        String scope
) {
}