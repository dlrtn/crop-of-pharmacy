package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto;

import static lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType.GOOGLE;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;

@JsonNaming(SnakeCaseStrategy.class)
public record GoogleMemberResponse(
        String sub,
        String email,
        String name,
        String picture
) {

    public OauthUserInfoDto toOauthUserInfoDto() {
        return OauthUserInfoDto.builder()
                .id(sub)
                .oauthServerId(sub)
                .oauthServiceType(GOOGLE.name())
                .nickname(name)
                .picture(picture)
                .build();
    }
}
