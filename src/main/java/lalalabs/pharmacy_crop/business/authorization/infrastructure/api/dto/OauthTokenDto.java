package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lalalabs.pharmacy_crop.business.authorization.domain.model.entity.OauthTokenEntity;
import lombok.Getter;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class OauthTokenDto {
    private String tokenType;
    private String accessToken;
    private String idToken;
    private String expriesIn;
    private String refreshToken;

    public OauthTokenEntity toEntity(String id) {
        return new OauthTokenEntity(id, accessToken, refreshToken, expriesIn);
    }
}
