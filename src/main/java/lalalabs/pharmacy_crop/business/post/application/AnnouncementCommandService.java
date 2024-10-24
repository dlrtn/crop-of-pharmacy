package lalalabs.pharmacy_crop.business.post.application;

import lalalabs.pharmacy_crop.business.post.api.dto.CreateAnnouncementRequest;
import lalalabs.pharmacy_crop.business.post.application.usecase.AnnouncementCommandServiceInterface;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementCommandService implements AnnouncementCommandServiceInterface {
    public void createAnnouncement(Long userId, CreateAnnouncementRequest request) {

    }

    public void updateAnnouncement(Long announcementId) {

    }

    public void deleteAnnouncement(Long announcementId) {

    }
}
