package lalalabs.pharmacy_crop.business.authorization.domain.model;

import static java.util.Locale.ENGLISH;

public enum OauthServiceType {

    KAKAO,
    GOOGLE,
    APPLE,
    ;

    public static OauthServiceType fromName(String type) {
        return OauthServiceType.valueOf(type.toUpperCase(ENGLISH));
    }
}
