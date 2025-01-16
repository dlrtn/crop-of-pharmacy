package lalalabs.pharmacy_crop.business.post.domain;

import jakarta.persistence.*;
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

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updatePicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
