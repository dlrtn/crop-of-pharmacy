package lalalabs.pharmacy_crop.common.push_notification.domain.model;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.dto.PushNotificationToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String userId;

    @Column
    private String token;

    @Column
    private Boolean isAgreePushNotification;

    public static UserFcmToken fromDto(String userId, PushNotificationToken token) {
        return UserFcmToken.builder()
                .userId(userId)
                .token(token.getToken())
                .isAgreePushNotification(token.getIsAgreePushNotification())
                .build();
    }

    public void updateToken(String token) {
        this.token = token;
    }

    public void updateAgreePushNotification(PushNotificationToken token) {
        this.token = token.getToken();
        this.isAgreePushNotification = token.getIsAgreePushNotification();
    }
}
