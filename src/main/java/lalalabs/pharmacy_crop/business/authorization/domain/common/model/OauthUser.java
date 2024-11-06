package lalalabs.pharmacy_crop.business.authorization.domain.common.model;

import static lombok.AccessLevel.PROTECTED;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class OauthUser {

    // todo 사용자 식별자를 Oauth Service에서 제공해주는 식별자로 사용할지..
    private Long id;

    private OauthId oauthId;

    private String nickname;

    public Long id() {
        return id;
    }

    public OauthId oauthId() {
        return oauthId;
    }

    public String nickname() {
        return nickname;
    }
}