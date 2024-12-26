package lalalabs.pharmacy_crop.common.push_notification.repository;

import lalalabs.pharmacy_crop.common.push_notification.domain.model.UserFcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFcmTokenRepository extends JpaRepository<UserFcmToken, Integer> {
    UserFcmToken findByUserId(String userId);
}
