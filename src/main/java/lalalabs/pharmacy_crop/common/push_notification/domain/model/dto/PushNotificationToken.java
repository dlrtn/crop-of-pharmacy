package lalalabs.pharmacy_crop.common.push_notification.domain.model.dto;

import lombok.Getter;

@Getter
public class PushNotificationToken {

    private String token;

    private Boolean isAgreePushNotification;
}
