package lalalabs.pharmacy_crop.business.user.api.dto;

import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String id;

    private String nickname;

    private String picture;

    private String role;

    public static UserDto from(OauthUser user) {
        return UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .picture(user.getPicture())
                .role(user.getRole().name())
                .build();
    }
}
