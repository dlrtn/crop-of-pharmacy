package lalalabs.pharmacy_crop.business.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthId;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OauthUser extends BaseTimeEntity {

    @Id
    @Builder.Default
    private String id = String.valueOf(java.util.UUID.randomUUID());

    @Embedded
    private OauthId oauthId;

    @Column
    private String nickname;

    @Column
    private String picture;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public static OauthUser create(OauthUserInfoDto oauthUserInfoDto) {
        return OauthUser.builder()
                .oauthId(new OauthId(oauthUserInfoDto.oauthServerId(),
                        OauthServiceType.valueOf(oauthUserInfoDto.oauthServiceType())))
                .nickname(oauthUserInfoDto.nickname())
                .picture(oauthUserInfoDto.picture())
                .role(Role.ROLE_OAUTH_USER)
                .build();
    }

    public void print() {
        System.out.println(id);
        System.out.println(oauthId.getOauthServiceType());
        System.out.println(oauthId.getOauthServerId());
        System.out.println(nickname);
        System.out.println(picture);
    }
}