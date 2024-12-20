package lalalabs.pharmacy_crop.business.post.api.dto;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuard;
import lalalabs.pharmacy_crop.common.time.TimeUtils;
import lombok.Builder;

@Builder
public class FarmGuardDto {

    private Long id;

    private String title;

    private String content;

    private String picture;

    private String createdAt;

    private boolean isReported;

    public static FarmGuardDto fromDomain(FarmGuard farmGuard, boolean isReported) {
        return FarmGuardDto.builder()
                .id(farmGuard.getId())
                .title(farmGuard.getTitle())
                .content(farmGuard.getContent())
                .picture("http://1.234.83.196:8080" + farmGuard.getPicturePath().substring(4))
                .createdAt(TimeUtils.convertToDateFormat(farmGuard.getCreatedDate()))
                .isReported(isReported)
                .build();
    }
}
