package lalalabs.pharmacy_crop.business.authorization.domain.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "authorization.oauth.google")
public class GoogleClientIdResolver {

    private List<String> clientIds;
}
