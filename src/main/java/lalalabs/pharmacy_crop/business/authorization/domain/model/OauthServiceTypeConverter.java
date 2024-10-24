package lalalabs.pharmacy_crop.business.authorization.domain.model;

import org.springframework.core.convert.converter.Converter;

public class OauthServiceTypeConverter implements Converter<String, OauthServiceType> {

    @Override
    public OauthServiceType convert(String serviceName) {
        return OauthServiceType.fromName(serviceName);
    }

}
