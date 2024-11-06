package lalalabs.pharmacy_crop.business.authorization.domain.google;

import static lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthServiceType.GOOGLE;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthId;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthUser;

@JsonNaming(SnakeCaseStrategy.class)
public record GoogleMemberResponse(
        String id,
        String email,
        String name,
        String picture
) {

    public OauthUser toDomain() {
        return OauthUser.builder()
                .oauthId(new OauthId(id, GOOGLE))
                .nickname(name)
                .build();
    }
}
