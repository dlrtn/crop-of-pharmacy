package lalalabs.pharmacy_crop.business.authorization.application.usecase;

import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthServiceType;

public interface AuthorizationCodeRequestUriProvider {

    OauthServiceType supportServer();

    String provide();

}
