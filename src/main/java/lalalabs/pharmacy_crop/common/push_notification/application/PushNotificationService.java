package lalalabs.pharmacy_crop.common.push_notification.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.PushNotificationBody;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.UserFcmToken;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.dto.PushNotificationToken;
import lalalabs.pharmacy_crop.common.push_notification.repository.UserFcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationService {

    private final UserFcmTokenRepository userFcmTokenRepository;

    @SneakyThrows
    public void send(String userId, PushNotificationBody body) {
        // send push notification
        UserFcmToken userFcmToken = userFcmTokenRepository.findByUserId(userId);

        Message message = Message.builder()
                .setToken(userFcmToken.getToken())
                .setNotification(body.toNotification())
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            if (e.getMessagingErrorCode().equals(MessagingErrorCode.INVALID_ARGUMENT)) {
                // 토큰이 유효하지 않은 경우, 오류 코드를 반환
            } else if (e.getMessagingErrorCode().equals(MessagingErrorCode.UNREGISTERED)) {
                // 재발급된 이전 토큰인 경우, 오류 코드를 반환
            }
            else { // 그 외, 오류는 런타임 예외로 처리
                throw new RuntimeException(e);
            }
        }
    }

    public void registerToken(String userId, PushNotificationToken token) {
        UserFcmToken userFcmToken = userFcmTokenRepository.findByUserId(userId);

        if (userFcmToken == null) {
            userFcmToken = UserFcmToken.fromDto(userId, token);
        }

        userFcmToken.updateToken(token.getToken());
        userFcmTokenRepository.save(userFcmToken);
    }
}
