package lalalabs.pharmacy_crop.business.authorization.infrastructure.usecase;

import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthUser;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.dto.OIDCPublicKeysResponse;

public interface OauthServiceClient {
    OauthServiceType supportServer();

    OauthUser fetch(String code);

    OIDCPublicKeysResponse getOIDCPublicKey();
}
