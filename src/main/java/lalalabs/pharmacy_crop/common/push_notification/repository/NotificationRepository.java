package lalalabs.pharmacy_crop.common.push_notification.repository;

import lalalabs.pharmacy_crop.common.push_notification.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserId(String userId, Pageable pageable);
}
