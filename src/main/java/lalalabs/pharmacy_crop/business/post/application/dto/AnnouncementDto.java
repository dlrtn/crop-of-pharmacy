package lalalabs.pharmacy_crop.business.post.application.dto;

import lalalabs.pharmacy_crop.business.post.domain.Announcement;
import lalalabs.pharmacy_crop.common.time.TimeUtils;
import lombok.Builder;

@Builder
public class AnnouncementDto {

    private Long id;

    private String title;

    private String content;

    private String picture;

    private String createdAt;

    public static AnnouncementDto fromDomain(Announcement announcement) {
        return AnnouncementDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .picture("http://1.234.83.196:8080/" + announcement.getPicturePath().substring(4))
                .createdAt(TimeUtils.convertToDateFormat(announcement.getCreatedDate()))
                .build();
    }
}
