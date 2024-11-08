package lalalabs.pharmacy_crop.business.authorization.domain.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OauthTokenDto;
import lombok.Getter;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class GoogleTokenDto extends OauthTokenDto {
    private String scope;
}
