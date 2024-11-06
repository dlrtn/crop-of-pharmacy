package lalalabs.pharmacy_crop.business.authorization.domain.common.converter;

import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthServiceType;
import org.springframework.core.convert.converter.Converter;

public class OauthServiceTypeConverter implements Converter<String, OauthServiceType> {

    @Override
    public OauthServiceType convert(String serviceName) {
        return OauthServiceType.fromName(serviceName);
    }

}
