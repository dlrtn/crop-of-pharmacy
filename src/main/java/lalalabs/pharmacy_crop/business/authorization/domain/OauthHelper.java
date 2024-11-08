package lalalabs.pharmacy_crop.business.authorization.domain;

import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OauthTokenDto;

public interface OauthHelper {

    OauthServiceType supportServer();

    OauthTokenDto fetchToken(String code);

    OIDCDecodePayload decode(OauthTokenDto oauthTokenDto);

    OauthUserInfoDto fetchUserInfo(OauthTokenDto accessToken);

    void unlink(String oauthId);
}
