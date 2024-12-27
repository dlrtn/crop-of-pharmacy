package lalalabs.pharmacy_crop.common.push_notification.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "firebase")
@Data
public class FirebaseProperties {

    private String jsonConfig;
}
