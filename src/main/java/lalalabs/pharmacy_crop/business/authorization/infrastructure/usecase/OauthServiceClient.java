package lalalabs.pharmacy_crop.business.authorization.infrastructure.usecase;

import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthUser;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;

public interface OauthServiceClient {
    OauthServiceType supportServer();

    OauthUser fetch(String code);
}
