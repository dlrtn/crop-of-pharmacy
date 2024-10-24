package lalalabs.pharmacy_crop.business.authorization.domain.model;

import static lombok.AccessLevel.PROTECTED;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class OauthId {

    private String oauthServerId;

    private OauthServiceType oauthServerType;

    public String oauthServerId() {
        return oauthServerId;
    }

    public OauthServiceType oauthServer() {
        return oauthServerType;
    }
}