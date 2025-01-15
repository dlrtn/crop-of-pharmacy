package lalalabs.pharmacy_crop.common.push_notification.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import lalalabs.pharmacy_crop.business.alarm.api.dto.NotificationDto;
import lalalabs.pharmacy_crop.common.push_notification.domain.Notification;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.PushNotificationBody;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.UserFcmToken;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.dto.PushNotificationToken;
import lalalabs.pharmacy_crop.common.push_notification.repository.NotificationRepository;
import lalalabs.pharmacy_crop.common.push_notification.repository.UserFcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationService {

    private final UserFcmTokenRepository userFcmTokenRepository;
    private final NotificationRepository notificationRepository;

    @SneakyThrows
    public void send(String userId, PushNotificationBody body) {
        // send push notification
        UserFcmToken userFcmToken = userFcmTokenRepository.findByUserId(userId);

        saveNotification(userId, body);

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

    public boolean isAgreePushNotification(String userId) {
        UserFcmToken userFcmToken = userFcmTokenRepository.findByUserId(userId);

        return userFcmToken.getIsAgreePushNotification();
    }

    public void updateAgreePushNotification(String userId, PushNotificationToken token) {
        UserFcmToken userFcmToken = userFcmTokenRepository.findByUserId(userId);

        if (userFcmToken == null) {
            userFcmToken = UserFcmToken.fromDto(userId, token);
        }

        userFcmToken.updateAgreePushNotification(token);
        userFcmTokenRepository.save(userFcmToken);
    }

    private void saveNotification(String userId, PushNotificationBody body) {
        Notification notification = Notification.from(userId, body);

        notificationRepository.save(notification);
    }

    public List<NotificationDto> getNotifications(String userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);

        return notifications.stream()
                .map(NotificationDto::fromDomain)
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .toList();
    }

    public void readNotification(String userId, int notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(RuntimeException::new);

        if (!notification.getUserId().equals(userId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        notification.read();
        notificationRepository.save(notification);
    }
}
