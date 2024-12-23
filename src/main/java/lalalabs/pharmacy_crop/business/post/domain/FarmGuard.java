package lalalabs.pharmacy_crop.business.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandFarmGuardRequest;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 병해충 잡초 찾기 도메인
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FarmGuard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String userId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String picturePath;

    @Column
    private boolean oftenViewed;

    public static FarmGuard create(CommandFarmGuardRequest request, OauthUser user, String picturePath) {
        return FarmGuard.builder()
                .userId(user.getId())
                .title(user.getNickname() + "님의 병해충 잡초 찾기")
                .content(request.getContent())
                .picturePath(picturePath)
                .oftenViewed(false)
                .build();
    }

    public boolean isOwner(String userId) {
        return this.userId.equals(userId);
    }

    public void updateOftenViewedStatus() {
        this.oftenViewed = !this.oftenViewed;
    }
}
