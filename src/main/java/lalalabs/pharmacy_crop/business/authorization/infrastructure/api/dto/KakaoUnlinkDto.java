package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(SnakeCaseStrategy.class)
public record KakaoUnlinkDto(
        String targetIdType,
        Long targetId
) {
    public KakaoUnlinkDto(Long targetId) {
        this("user_id", targetId);
    }
}
