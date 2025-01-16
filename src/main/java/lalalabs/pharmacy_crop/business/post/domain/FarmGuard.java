package lalalabs.pharmacy_crop.business.post.domain;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandFarmGuardRequest;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "farm_guard")
public class FarmGuard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "picture_path")
    private String picturePath;

    @Column(name = "often_viewed")
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
