package lalalabs.pharmacy_crop.business.post.api.dto;

import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WriterInformation {

    private String userId;

    private String nickname;

    public static WriterInformation from(OauthUser user) {
        return WriterInformation.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
