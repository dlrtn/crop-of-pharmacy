package lalalabs.pharmacy_crop.business.authorization.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lalalabs.pharmacy_crop.business.authorization.domain.model.entity.OauthTokenEntity;
import lombok.Getter;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class OauthTokenDto {
    @NotBlank
    private String tokenType;

    @NotBlank
    private String accessToken;

    @NotBlank
    private String idToken;

    public OauthTokenEntity toEntity(String id) {
        return new OauthTokenEntity(id, accessToken);
    }
}
