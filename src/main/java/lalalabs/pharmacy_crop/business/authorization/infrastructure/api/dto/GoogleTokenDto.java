package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class GoogleTokenDto extends OauthTokenDto {
    private String scope;
}
