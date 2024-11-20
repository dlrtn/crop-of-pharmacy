package lalalabs.pharmacy_crop.business.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;

// 공지사항 도메인
@Entity
public class Announcement extends BaseTimeEntity {

    @Id
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String writer;

    @Column
    private String picture;

    @Column
    private String link;

}
