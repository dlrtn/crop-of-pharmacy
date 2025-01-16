package lalalabs.pharmacy_crop.common.push_notification.domain;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.PushNotificationBody;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "is_read")
    private boolean isRead;

    public static Notification from(String userId, PushNotificationBody body) {
        return Notification.builder().userId(userId).title(body.getTitle()).body(body.getBody()).isRead(false).build();
    }

    public void read() {
        this.isRead = true;
    }
}
