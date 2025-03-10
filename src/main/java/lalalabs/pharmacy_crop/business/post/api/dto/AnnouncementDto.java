package lalalabs.pharmacy_crop.business.post.api.dto;

import lalalabs.pharmacy_crop.business.post.domain.Announcement;
import lalalabs.pharmacy_crop.common.time.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
                .picture(String.format("http://1.234.83.196:8080%s", announcement.getPicturePath() != null ? announcement.getPicturePath().substring(4) : null))
                .createdAt(TimeUtils.convertToDateFormat(announcement.getCreatedDate()))
                .build();
    }
}
