package lalalabs.pharmacy_crop.business.post.api.dto;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuard;
import lalalabs.pharmacy_crop.business.post.domain.FarmGuardAnswer;
import lalalabs.pharmacy_crop.common.time.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmGuardDetailDto {

    private Long id;

    private String title;

    private String content;

    private String imagePath;

    private String date;

    private FarmGuardAnswerDto answer;

    public static FarmGuardDetailDto fromDomain(FarmGuard farmGuard, FarmGuardAnswer answer) {
        return FarmGuardDetailDto.builder()
                .id(farmGuard.getId())
                .title(farmGuard.getTitle())
                .content(farmGuard.getContent())
                .imagePath("http://1.234.83.196:8080" + farmGuard.getPicturePath().substring(4))
                .date(TimeUtils.convertToDateFormat(farmGuard.getCreatedDate()))
                .answer(FarmGuardAnswerDto.fromDomain(answer))
                .build();
    }

    public static FarmGuardDetailDto fromDomain(FarmGuard farmGuard) {
        return FarmGuardDetailDto.builder()
                .id(farmGuard.getId())
                .title(farmGuard.getTitle())
                .content(farmGuard.getContent())
                .imagePath("http://1.234.83.196:8080" + farmGuard.getPicturePath().substring(4))
                .date(TimeUtils.convertToDateFormat(farmGuard.getCreatedDate()))
                .build();
    }
}
