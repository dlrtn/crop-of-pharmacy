package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto;

import static lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType.KAKAO;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoMemberResponse(
        Long id,
        boolean hasSignedUp,
        LocalDateTime connectedAt,
        KakaoAccount kakaoAccount
) {

    public OauthUserInfoDto toOauthUserInfoDto() {
        return OauthUserInfoDto.builder()
                .id(String.valueOf(id))
                .oauthServerId(String.valueOf(id))
                .oauthServiceType(KAKAO.name())
                .nickname(kakaoAccount.profile.nickname)
                .picture(kakaoAccount.profile.profileImageUrl)
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
