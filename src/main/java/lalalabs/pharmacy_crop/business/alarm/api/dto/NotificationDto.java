package lalalabs.pharmacy_crop.business.alarm.api.dto;

import lalalabs.pharmacy_crop.common.push_notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private int id;

    private String userId;

    private String title;

    private String body;

    private LocalDateTime createdAt;

    private Boolean isRead;

    public static NotificationDto fromDomain(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .title(notification.getTitle())
                .body(notification.getBody())
                .createdAt(notification.getCreatedDate())
                .isRead(notification.isRead())
                .build();
    }
}
