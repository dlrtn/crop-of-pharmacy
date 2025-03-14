package lalalabs.pharmacy_crop.common.push_notification.domain.model;

import com.google.firebase.messaging.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PushNotificationBody {

    private String title;

    private String body;

    public Notification toNotification() {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
    }
}
