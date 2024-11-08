package lalalabs.pharmacy_crop.business.authorization.api.dto;

import lombok.Builder;

@Builder
public record JwtTokens(
        String accessToken,
        String refreshToken
) {
}
