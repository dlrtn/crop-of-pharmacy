package lalalabs.pharmacy_crop.business.post.application;

import lalalabs.pharmacy_crop.common.push_notification.application.PushNotificationService;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.PushNotificationBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnnouncementPushNotificationSender {

    private final PushNotificationService pushNotificationService;

    public void sendAnnouncementPushNotification() {
        PushNotificationBody body = PushNotificationBody.builder()
                .title("새로운 공지사항이 등록되었습니다.")
                .body("")
                .build();

        pushNotificationService.sendAll(body);
    }
}
