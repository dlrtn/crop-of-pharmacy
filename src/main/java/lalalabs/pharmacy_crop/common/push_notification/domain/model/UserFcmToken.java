package lalalabs.pharmacy_crop.common.push_notification.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class UserFcmToken {

    @Id
    private int id;

    @Column
    private String userId;

    @Column
    private String token;
}
