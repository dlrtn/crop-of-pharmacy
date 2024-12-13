package lalalabs.pharmacy_crop.business.authorization.domain.model.dto;

import lombok.Builder;

@Builder
public record OauthUserInfoDto(
        String id,
        String oauthServerId,
        String oauthServiceType,
        String nickname,
        String picture
) {
}
