package lalalabs.pharmacy_crop.business.post.application.dto;

import java.time.LocalDateTime;
import lalalabs.pharmacy_crop.business.post.domain.Announcement;
import lombok.Builder;

@Builder
public class AnnouncementDto {

    private Long id;

    private String title;

    private String content;

    private String picture;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static AnnouncementDto fromDomain(Announcement announcement) {
        return AnnouncementDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .picture(announcement.getPicturePath())
                .createdAt(announcement.getCreatedDate())
                .updatedAt(announcement.getModifiedDate())
                .build();
    }
}
