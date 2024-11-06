package lalalabs.pharmacy_crop.business.authorization.domain.kakao.dto;

import static lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthServiceType.KAKAO;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthId;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthUser;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoMemberResponse(
        Long id,
        boolean hasSignedUp,
        LocalDateTime connectedAt,
        KakaoAccount kakaoAccount
) {

    public OauthUser toDomain() {
        return OauthUser.builder()
                .oauthId(new OauthId(String.valueOf(id), KAKAO))
                .nickname(kakaoAccount.profile.nickname)
                .build();
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record KakaoAccount(
            Long id,
            Profile profile
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record Profile(
            String nickname,
            String thumbnailImageUrl,
            String profileImageUrl,
            boolean isDefaultImage
    ) {
    }

}
