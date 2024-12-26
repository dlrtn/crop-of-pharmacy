package lalalabs.pharmacy_crop.business.post.api.dto;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuard;
import lalalabs.pharmacy_crop.common.time.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmGuardDto {

    private Long id;

    private String title;

    private String content;

    private String imagePath;

    private String date;

    private Boolean isComplained;

    private Boolean isAnswered;

    public static FarmGuardDto fromDomain(FarmGuard farmGuard, boolean isReported, boolean isAnswered) {
        return FarmGuardDto.builder()
                .id(farmGuard.getId())
                .title(farmGuard.getTitle())
                .content(farmGuard.getContent())
                .imagePath("http://1.234.83.196:8080" + farmGuard.getPicturePath().substring(4))
                .date(TimeUtils.convertToDateFormat(farmGuard.getCreatedDate()))
                .isComplained(isReported)
                .isAnswered(isAnswered)
                .build();
    }
}
