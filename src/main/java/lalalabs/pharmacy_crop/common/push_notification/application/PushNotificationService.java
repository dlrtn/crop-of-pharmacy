package lalalabs.pharmacy_crop.common.push_notification.application;

import lalalabs.pharmacy_crop.common.push_notification.domain.model.PushNotificationBody;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.UserFcmToken;
import lalalabs.pharmacy_crop.common.push_notification.repository.UserFcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushNotificationService {

    private final UserFcmTokenRepository userFcmTokenRepository;

    public void send(String userId, PushNotificationBody body) {
        // send push notification
        UserFcmToken userFcmToken = userFcmTokenRepository.findByUserId(userId);

        // send push notification to userFcmToken.getToken()


    }
}
