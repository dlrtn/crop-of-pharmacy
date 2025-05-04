package lalalabs.pharmacy_crop.business.post.domain;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandAnnouncementRequest;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement")
public class Announcement extends BaseTimeEntity {

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

    public static Announcement create(String userId, CommandAnnouncementRequest request) {
        return Announcement.builder()
                .userId(userId)
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    public void update(CommandAnnouncementRequest request) {
        this.title = request.getTitle() == null ? this.title : request.getTitle();
        this.content = request.getContent() == null ? this.content : request.getContent();
    }

    public void updatePicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
