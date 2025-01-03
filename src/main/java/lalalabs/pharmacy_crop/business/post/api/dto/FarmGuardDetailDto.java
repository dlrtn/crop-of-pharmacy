package lalalabs.pharmacy_crop.business.post.api.dto;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuard;
import lalalabs.pharmacy_crop.business.post.domain.FarmGuardAnswer;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
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

    private WriterInformation writerInformation;

    public static FarmGuardDetailDto fromDomain(FarmGuard farmGuard, FarmGuardAnswer answer, OauthUser user) {
        return FarmGuardDetailDto.builder()
                .id(farmGuard.getId())
                .title(getTitle(user))
                .content(farmGuard.getContent())
                .imagePath("http://1.234.83.196:8080" + farmGuard.getPicturePath().substring(4))
                .date(TimeUtils.convertToDateFormat(farmGuard.getCreatedDate()))
                .answer(FarmGuardAnswerDto.fromDomain(answer))
                .writerInformation(WriterInformation.from(user))
                .build();
    }

    public static FarmGuardDetailDto fromDomain(FarmGuard farmGuard, OauthUser user) {
        return FarmGuardDetailDto.builder()
                .id(farmGuard.getId())
                .title(getTitle(user))
                .content(farmGuard.getContent())
                .imagePath("http://1.234.83.196:8080" + farmGuard.getPicturePath().substring(4))
                .date(TimeUtils.convertToDateFormat(farmGuard.getCreatedDate()))
                .writerInformation(WriterInformation.from(user))
                .build();
    }

    private static String getTitle(OauthUser user) {
        return user.getNickname() + "님의 병충해 잡초 찾기";
    }

}
