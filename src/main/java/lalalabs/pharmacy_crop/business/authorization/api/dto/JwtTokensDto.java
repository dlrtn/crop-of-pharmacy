package lalalabs.pharmacy_crop.business.authorization.api.dto;

import lombok.Builder;

@Builder
public record JwtTokensDto(
        String accessToken,
        String refreshToken
) {
}
