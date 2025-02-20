package lalalabs.pharmacy_crop.business.user.domain;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.business.authorization.domain.model.UsernamePassword;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthId;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.user.api.dto.RegisterAdminRequest;
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
@Table(name = "oauth_user")
public class OauthUser extends BaseTimeEntity {

    @Id
    @Builder.Default
    private String id = String.valueOf(java.util.UUID.randomUUID());

    @Embedded
    @Column(name = "oauth_id")
    private OauthId oauthId;

    @Embedded
    @Column(name = "username_password")
    private UsernamePassword usernamePassword;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "picture")
    private String picture;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "deleted")
    private Boolean deleted;

    public static OauthUser create(OauthUserInfoDto oauthUserInfoDto) {
        return OauthUser.builder()
                .oauthId(new OauthId(oauthUserInfoDto.oauthServerId(),
                        OauthServiceType.valueOf(oauthUserInfoDto.oauthServiceType())))
                .nickname(oauthUserInfoDto.nickname())
                .picture(oauthUserInfoDto.picture())
                .role(Role.ROLE_MEMBER)
                .deleted(false)
                .build();
    }

    public static OauthUser createAdmin(RegisterAdminRequest request, String encodedPassword) {
        return OauthUser.builder()
                .usernamePassword(new UsernamePassword(request.username(), encodedPassword))
                .nickname(request.nickname())
                .role(Role.ROLE_ADMIN)
                .deleted(false)
                .build();
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeAuthority(Role authority) {
        this.role = authority;
    }

    public void delete() {
        this.nickname = "탈퇴한 사용자";
        this.deleted = true;
    }

    public void restore(OauthUserInfoDto oauthUserInfo) {
        this.nickname = oauthUserInfo.nickname();
        this.picture = oauthUserInfo.picture();
        this.deleted = false;
    }
}